package org.example.revconnect.Service;

import org.example.revconnect.Models.Connection;
import org.example.revconnect.Models.ConnectionStatus;
import org.example.revconnect.Repository.ConnectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private ConnectionService connectionService;

    // SEND REQUEST

    @Test
    void testSendRequestSuccess() {
        when(connectionRepository.existsBySenderIdAndReceiverId(1L, 2L))
                .thenReturn(false);

        connectionService.sendRequest(1L, 2L);

        verify(connectionRepository).save(argThat(connection ->
                connection.getSenderId() == 1L &&
                        connection.getReceiverId() == 2L &&
                        connection.getStatus() == ConnectionStatus.PENDING
        ));
    }

    @Test
    void testSendRequestAlreadyExists() {
        when(connectionRepository.existsBySenderIdAndReceiverId(1L, 2L))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> connectionService.sendRequest(1L, 2L)
        );

        assertEquals("Connection request already exists", ex.getMessage());
        verify(connectionRepository, never()).save(any());
    }

    // VIEW PENDING

    @Test
    void testGetPendingRequests() {
        Connection c1 = new Connection();
        Connection c2 = new Connection();

        when(connectionRepository.findByReceiverIdAndStatus(
                2L, ConnectionStatus.PENDING
        )).thenReturn(List.of(c1, c2));

        List<Connection> result =
                connectionService.getPendingRequests(2L);

        assertEquals(2, result.size());
    }

    // ACCEPT REQUEST

    @Test
    void testAcceptRequestSuccess() {
        Connection connection = new Connection();
        connection.setStatus(ConnectionStatus.PENDING);

        when(connectionRepository.findById(10L))
                .thenReturn(Optional.of(connection));

        connectionService.acceptRequest(10L);

        assertEquals(ConnectionStatus.ACCEPTED, connection.getStatus());
        verify(connectionRepository).save(connection);
    }

    @Test
    void testAcceptRequestNotFound() {
        when(connectionRepository.findById(10L))
                .thenReturn(Optional.empty());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> connectionService.acceptRequest(10L)
        );

        assertEquals("Request not found", ex.getMessage());
    }

    // REJECT REQUEST

    @Test
    void testRejectRequestSuccess() {
        Connection connection = new Connection();
        connection.setStatus(ConnectionStatus.PENDING);

        when(connectionRepository.findById(20L))
                .thenReturn(Optional.of(connection));

        connectionService.rejectRequest(20L);

        assertEquals(ConnectionStatus.REJECTED, connection.getStatus());
        verify(connectionRepository).save(connection);
    }

    //VIEW CONNECTIONS

    @Test
    void testGetConnections() {
        Connection c1 = new Connection();
        Connection c2 = new Connection();

        when(connectionRepository.findByStatusAndSenderIdOrStatusAndReceiverId(
                ConnectionStatus.ACCEPTED, 1L,
                ConnectionStatus.ACCEPTED, 1L
        )).thenReturn(List.of(c1, c2));

        List<Connection> result =
                connectionService.getConnections(1L);

        assertEquals(2, result.size());
    }
}
