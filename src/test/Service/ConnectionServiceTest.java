package Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionServiceTest {

    ConnectionService connectionService = new ConnectionService();

    // User 1 sends connection request to User 2
    @Test
    void sendConnectionRequest_success() {
        assertDoesNotThrow(() ->
                connectionService.sendRequest(1L, 2L)
        );
    }

    // View pending requests for User 2
    @Test
    void viewPendingRequests_success() {
        assertDoesNotThrow(() ->
                connectionService.viewPending(2L)
        );
    }

    // Accept a connection request by request ID
    // NOTE: requestId must exist in DB
    @Test
    void acceptRequest_success() {
        long requestId = 1L; // make sure this exists
        assertDoesNotThrow(() ->
                connectionService.accept(requestId)
        );
    }

    // Reject a connection request
    @Test
    void rejectRequest_success() {
        long requestId = 1L; // same or another valid request
        assertDoesNotThrow(() ->
                connectionService.reject(requestId)
        );
    }

    // View connections of User 1
    @Test
    void viewConnections_success() {
        assertDoesNotThrow(() ->
                connectionService.viewConnections(1L)
        );
    }
}
