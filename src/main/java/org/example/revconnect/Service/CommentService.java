package org.example.revconnect.Service;

import org.example.revconnect.Repository.CommentRepository;
import org.example.revconnect.Models.Comment;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // Constructor injection
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // ADD comment
    public void addComment(Long userId, Long postId, String text) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setText(text);

        commentRepository.save(comment);
    }

    // VIEW comments (returns data, does NOT print)
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // DELETE comment
    public boolean deleteComment(Long commentId, Long userId) {
        if (!commentRepository.existsById(commentId)) {
            return false;
        }

        commentRepository.deleteByIdAndUserId(commentId, userId);
        return true;
    }
}
