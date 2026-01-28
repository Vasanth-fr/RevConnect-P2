package Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeServiceTest {

    LikeService likeService = new LikeService();

    @Test
    void likePost_success() {
        assertDoesNotThrow(() ->
                likeService.like(1L, 1L)
        );
    }

    @Test
    void unlikePost_success() {
        assertDoesNotThrow(() ->
                likeService.unlike(1L, 1L)
        );
    }
}
