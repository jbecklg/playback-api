package playback.api.model;

public class CustomerInfo {

    private boolean isActive;
    private CustomerTier customerTier;

    public CustomerInfo(boolean isActive, CustomerTier customerTier) {
        this.isActive = isActive;
        this.customerTier = customerTier;
    }

    public boolean isActive() {
        return isActive;
    }

    public CustomerTier getCustomerTier() {
        return customerTier;
    }
}
