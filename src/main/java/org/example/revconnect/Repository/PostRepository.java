package org.example.revconnect.Repository;

import org.example.revconnect.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Get all posts created by a user
    List<Post> findByUserId(Long userId);

    // Delete a post only if it belongs to the user
    void deleteByIdAndUserId(Long id, Long userId);
}
