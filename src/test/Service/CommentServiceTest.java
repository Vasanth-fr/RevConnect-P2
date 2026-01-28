package Service;

import Models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    private long userId;
    private long postId;

    @BeforeEach
    void setup() {
        userService = new UserService();
        postService = new PostService();
        commentService = new CommentService();

        // UNIQUE user every test run
        String email = "comment_" + System.currentTimeMillis() + "@mail.com";

        userService.register(email, "1234", "PERSONAL");
        userId = userService.login(email, "1234").getId();

        // Create post and CAPTURE REAL postId
        postService.createPost(userId, "Post for comment test");

        List<Post> posts = postService.getMyPosts(userId);
        assertFalse(posts.isEmpty());

        postId = posts.get(0).getId();
    }

    @Test
    void addComment_success() {
        assertDoesNotThrow(() ->
                commentService.addComment(userId, postId, "JUnit comment")
        );
    }

    @Test
    void viewComments_success() {
        commentService.addComment(userId, postId, "Another JUnit comment");

        assertDoesNotThrow(() ->
                commentService.viewComments(postId)
        );
    }
}
