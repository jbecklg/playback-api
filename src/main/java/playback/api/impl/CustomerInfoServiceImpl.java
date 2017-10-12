package playback.api.impl;

import org.springframework.stereotype.Service;
import playback.api.model.CustomerInfo;
import playback.api.model.CustomerTier;
import playback.api.service.CustomerInfoService;

import java.util.UUID;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    public CustomerInfo getCustomerInfo(UUID userId) {
        return new CustomerInfo(true, CustomerTier.PREMIUM);
    }
}
