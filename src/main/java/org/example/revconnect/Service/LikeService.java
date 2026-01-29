package org.example.revconnect.Service;

import org.example.revconnect.Models.Like;
import org.example.revconnect.Repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // LIKE a post
    public void likePost(Long userId, Long postId) {

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalStateException("Post already liked");
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setPostId(postId);

        likeRepository.save(like);
    }

    // UNLIKE a post
    public void unlikePost(Long userId, Long postId) {

        if (!likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalStateException("Like not found");
        }

        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
