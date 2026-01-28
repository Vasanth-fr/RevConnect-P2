package org.example.revconnect.Repository;

import org.example.revconnect.Models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // Check if user already liked the post
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // Unlike (delete)
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
