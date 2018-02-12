package main.java.playback.mock;

import main.java.customer.CustomerInfo;
import main.java.customer.CustomerInfo.Plan;
import main.java.customer.CustomerInfo.Status;
import main.java.playback.api.CustomerInfoService;
import main.java.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MockCustomerInfoServiceImpl implements CustomerInfoService {
    private static Map<String, CustomerInfo> mockSessionIdToCustomerInfoMap = new HashMap<>();
    static {
        mockSessionIdToCustomerInfoMap.put("session1_active_premium", new CustomerInfo(Status.ACTIVE, Plan.PREMIUM));
        mockSessionIdToCustomerInfoMap.put("session2_active_standard", new CustomerInfo(Status.ACTIVE, Plan.STANDARD));
        mockSessionIdToCustomerInfoMap.put("session3_inactive", new CustomerInfo(Status.INACTIVE, Plan.PREMIUM));
    }

    @Override
    public CompletableFuture<CustomerInfo> getCustomerInfo(Session deviceSession, Executor executor) {
        return CompletableFuture.supplyAsync(() -> getCustomerInfoInternal(deviceSession), executor);
    }

    private CustomerInfo getCustomerInfoInternal(Session deviceSession) {
        try {
            // Fake service latency.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return mockSessionIdToCustomerInfoMap.get(deviceSession.getSessionId());
    }
}
