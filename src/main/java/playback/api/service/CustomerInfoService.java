package playback.api.service;

import playback.api.model.CustomerInfo;

import java.util.UUID;

public interface CustomerInfoService {

    public CustomerInfo getCustomerInfo(UUID userId);

}