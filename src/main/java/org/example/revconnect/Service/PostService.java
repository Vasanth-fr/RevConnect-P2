package org.example.revconnect.Service;

import org.example.revconnect.Models.Post;
import org.example.revconnect.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // CREATE post
    public void createPost(Long userId, String content) {

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty");
        }

        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);

        postRepository.save(post);
    }

    // VIEW user's posts
    public List<Post> getMyPosts(Long userId) {
        return postRepository.findByUserId(userId);
    }

    // DELETE post
    public void deletePost(Long postId, Long userId) {

        if (!postRepository.existsById(postId)) {
            throw new IllegalStateException("Post not found");
        }

        postRepository.deleteByIdAndUserId(postId, userId);
    }
}
