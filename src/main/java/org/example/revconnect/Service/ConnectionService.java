package org.example.revconnect.Service;

import org.example.revconnect.Models.Connection;
import org.example.revconnect.Models.ConnectionStatus;
import org.example.revconnect.Repository.ConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    // Constructor injection
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    // SEND connection request
    public void sendRequest(Long senderId, Long receiverId) {

        boolean exists = connectionRepository
                .existsBySenderIdAndReceiverId(senderId, receiverId);

        if (exists) {
            throw new IllegalStateException("Connection request already exists");
        }

        Connection connection = new Connection();
        connection.setSenderId(senderId);
        connection.setReceiverId(receiverId);
        connection.setStatus(ConnectionStatus.PENDING);

        connectionRepository.save(connection);
    }

    // VIEW pending requests
    public List<Connection> getPendingRequests(Long userId) {
        return connectionRepository
                .findByReceiverIdAndStatus(userId, ConnectionStatus.PENDING);
    }

    // ACCEPT request
    public void acceptRequest(Long requestId) {

        Connection connection = connectionRepository.findById(requestId)
                .orElseThrow(() -> new IllegalStateException("Request not found"));

        connection.setStatus(ConnectionStatus.ACCEPTED);
        connectionRepository.save(connection);
    }

    // REJECT request
    public void rejectRequest(Long requestId) {

        Connection connection = connectionRepository.findById(requestId)
                .orElseThrow(() -> new IllegalStateException("Request not found"));

        connection.setStatus(ConnectionStatus.REJECTED);
        connectionRepository.save(connection);
    }

    // VIEW accepted connections
    public List<Connection> getConnections(Long userId) {
        return connectionRepository
                .findByStatusAndSenderIdOrStatusAndReceiverId(
                        ConnectionStatus.ACCEPTED, userId,
                        ConnectionStatus.ACCEPTED, userId
                );
    }
}
