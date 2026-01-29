package org.example.revconnect.Service;

import org.example.revconnect.Models.Post;
import org.example.revconnect.Repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    //CREATE POST
    @Test
    void testCreatePostSuccess() {
        postService.createPost(1L, "Hello World");

        verify(postRepository).save(argThat(post ->
                post.getUserId().equals(1L) &&
                        post.getContent().equals("Hello World")
        ));
    }

    // EMPTY POST
    @Test
    void testCreatePostEmptyContent() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> postService.createPost(1L, "   ")
        );

        assertEquals("Post content cannot be empty", ex.getMessage());
        verify(postRepository, never()).save(any());
    }

    // NULL POST
    @Test
    void testCreatePostNullContent() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> postService.createPost(1L, null)
        );

        assertEquals("Post content cannot be empty", ex.getMessage());
        verify(postRepository, never()).save(any());
    }

    // VIEW POSTS
    @Test
    void testGetMyPosts() {
        Post p1 = new Post();
        p1.setContent("First");

        Post p2 = new Post();
        p2.setContent("Second");

        when(postRepository.findByUserId(1L))
                .thenReturn(List.of(p1, p2));

        List<Post> posts = postService.getMyPosts(1L);

        assertEquals(2, posts.size());
        verify(postRepository).findByUserId(1L);
    }

    // DELETE POST
    @Test
    void testDeletePostSuccess() {
        when(postRepository.existsById(10L))
                .thenReturn(true);

        postService.deletePost(10L, 1L);

        verify(postRepository)
                .deleteByIdAndUserId(10L, 1L);
    }

    // DELETED POST NOT FOUND
    @Test
    void testDeletePostNotFound() {
        when(postRepository.existsById(10L))
                .thenReturn(false);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> postService.deletePost(10L, 1L)
        );

        assertEquals("Post not found", ex.getMessage());
        verify(postRepository, never())
                .deleteByIdAndUserId(any(), any());
    }
}
