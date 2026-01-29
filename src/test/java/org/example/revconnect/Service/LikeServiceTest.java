package org.example.revconnect.Service;

import org.example.revconnect.Repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    // ---------------- LIKE POST ----------------

    @Test
    void testLikePostSuccess() {
        when(likeRepository.existsByUserIdAndPostId(1L, 10L))
                .thenReturn(false);

        likeService.likePost(1L, 10L);

        verify(likeRepository).save(argThat(like ->
                like.getUserId() == 1L &&
                        like.getPostId() == 10L
        ));
    }

    @Test
    void testLikePostAlreadyLiked() {
        when(likeRepository.existsByUserIdAndPostId(1L, 10L))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> likeService.likePost(1L, 10L)
        );

        assertEquals("Post already liked", ex.getMessage());
        verify(likeRepository, never()).save(any());
    }

    // ---------------- UNLIKE POST ----------------

    @Test
    void testUnlikePostSuccess() {
        when(likeRepository.existsByUserIdAndPostId(1L, 10L))
                .thenReturn(true);

        likeService.unlikePost(1L, 10L);

        verify(likeRepository)
                .deleteByUserIdAndPostId(1L, 10L);
    }

    @Test
    void testUnlikePostNotFound() {
        when(likeRepository.existsByUserIdAndPostId(1L, 10L))
                .thenReturn(false);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> likeService.unlikePost(1L, 10L)
        );

        assertEquals("Like not found", ex.getMessage());
        verify(likeRepository, never())
                .deleteByUserIdAndPostId(any(), any());
    }
}
