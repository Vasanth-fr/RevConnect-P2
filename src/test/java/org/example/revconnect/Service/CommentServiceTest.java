package org.example.revconnect.Service;

import org.example.revconnect.Models.Comment;
import org.example.revconnect.Repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    // ADD COMMENT
    @Test
    void testAddCommentSuccess() {
        commentService.addComment(1L, 10L, "Nice post!");
        verify(commentRepository).save(argThat(comment ->
                comment.getUserId().equals(1L) &&
                        comment.getPostId() == 10L &&
                        comment.getText().equals("Nice post!")
        ));
    }

    // VIEW COMMENTS
    @Test
    void testGetCommentsByPost() {
        // Arrange
        Comment c1 = new Comment();
        c1.setText("First");

        Comment c2 = new Comment();
        c2.setText("Second");

        when(commentRepository.findByPostId(10L))
                .thenReturn(List.of(c1, c2));
        List<Comment> result = commentService.getCommentsByPost(10L);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("First", result.get(0).getText());
        assertEquals("Second", result.get(1).getText());

        verify(commentRepository).findByPostId(10L);
    }

    // DELETE COMMENT (SUCCESS)
    @Test
    void testDeleteCommentSuccess() {
        // Arrange
        when(commentRepository.existsById(5L))
                .thenReturn(true);
        boolean result = commentService.deleteComment(5L, 1L);
        assertTrue(result);
        verify(commentRepository).deleteByIdAndUserId(5L, 1L);
    }

    // DELETE COMMENT (FAILURE)
    @Test
    void testDeleteCommentNotFound() {
        when(commentRepository.existsById(99L))
                .thenReturn(false);
        boolean result = commentService.deleteComment(99L, 1L);
        assertFalse(result);
        verify(commentRepository, never())
                .deleteByIdAndUserId(anyLong(), anyLong());
    }
}
