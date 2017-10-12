package playback.api;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import playback.api.model.CustomerInfo;
import playback.api.model.CustomerTier;
import playback.api.model.DeviceType;
import playback.api.service.CustomerInfoService;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaybackControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerInfoService customerInfoService;

    public PlaybackControllerTest() {
    }

    @Test
    public void testUnauthorizedCustomer() throws Exception {
        UUID titleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Mockito.when(customerInfoService.getCustomerInfo(userId))
                .thenReturn(new CustomerInfo(false, CustomerTier.PREMIUM));
        String urlParams = "userId="+userId+"&titleId="+titleId+"&deviceType="+ DeviceType.APPLE_IOS.toString();
        mvc.perform(MockMvcRequestBuilders.get("/?"+urlParams)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAuthorizedCustomer() throws Exception {
        UUID titleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Mockito.when(customerInfoService.getCustomerInfo(userId))
                .thenReturn(new CustomerInfo(true, CustomerTier.PREMIUM));
        String urlParams = "userId="+userId+"&titleId="+titleId+"&deviceType="+ DeviceType.APPLE_TVOS.toString();

        mvc.perform(MockMvcRequestBuilders.get("/?"+urlParams)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.*", Matchers.hasSize(4))))
                .andExpect((jsonPath("$.AUDIO_AAC").exists()))
                .andExpect((jsonPath("$.VIDEO_H264_720P").exists()))
                .andExpect((jsonPath("$.VIDEO_H264_1080P").exists()))
                .andExpect((jsonPath("$.VIDEO_H264_2160P").exists()))
                .andExpect((jsonPath("$.AUDIO_AAC.[0].urls", Matchers.hasSize(3))));
    }

    @Test
    public void testNonPremiumCustomer() throws Exception {
        UUID titleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Mockito.when(customerInfoService.getCustomerInfo(userId))
                .thenReturn(new CustomerInfo(true, CustomerTier.BASIC));
        String urlParams = "userId="+userId+"&titleId="+titleId+"&deviceType="+ DeviceType.APPLE_TVOS.toString();

        mvc.perform(MockMvcRequestBuilders.get("/?"+urlParams)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.*", Matchers.hasSize(3))))
                .andExpect((jsonPath("$.AUDIO_AAC").exists()))
                .andExpect((jsonPath("$.VIDEO_H264_720P").exists()))
                .andExpect((jsonPath("$.VIDEO_H264_1080P").exists()))
                .andExpect((jsonPath("$.AUDIO_AAC.[0].urls", Matchers.hasSize(3))));
    }
}
