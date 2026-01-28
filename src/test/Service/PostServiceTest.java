package Service;

import Models.Post;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    PostService postService = new PostService();

    @Test
    void createPost_success() {
        boolean result = postService.createPost(1L, "JUnit test post");
        assertTrue(result);
    }

    @Test
    void getMyPosts_success() {

        long userId = 1L;

        // Ensure at least one post exists
        postService.createPost(userId, "JUnit test post");

        List<Post> posts = postService.getMyPosts(userId);

        assertNotNull(posts);
        assertFalse(posts.isEmpty());
    }
}
