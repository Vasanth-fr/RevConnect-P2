package org.example.revconnect.Repository;

import org.example.revconnect.Models.Connection;
import org.example.revconnect.Models.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    // Send request
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // Get pending requests for a user
    List<Connection> findByReceiverIdAndStatus(Long receiverId, ConnectionStatus status);

    // Update status (handled via save)
    Optional<Connection> findById(Long id);

    // Get accepted connections involving a user
    List<Connection> findByStatusAndSenderIdOrStatusAndReceiverId(
            ConnectionStatus status1, Long senderId,
            ConnectionStatus status2, Long receiverId
    );
}
