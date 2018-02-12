package main.java.customer;

public class CustomerInfo {
    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Plan {
        BASIC,
        STANDARD,
        PREMIUM
    }

    private Status status;
    private Plan plan;

    public CustomerInfo(Status status, Plan plan) {
        this.status = status;
        this.plan = plan;
    }

    public Status getStatus() {
        return status;
    }

    public Plan getPlan() {
        return plan;
    }
}
