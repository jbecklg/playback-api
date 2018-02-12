package main.java.playback.api;

import main.java.customer.CustomerInfo;
import main.java.session.Session;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * CustomerInfoService (external service).
 */
public interface CustomerInfoService {
    /**
     * Gets the customer information from a session.
     *
     * @param session User's session.
     * @param executor Executor to run the network call.
     * @return Customer information (plan, status, etc.) future.
     */
    CompletableFuture<CustomerInfo> getCustomerInfo(Session session,
                                                    Executor executor);
}
