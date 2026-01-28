package org.example.revconnect;

import org.example.revconnect.Models.*;
import org.example.revconnect.Service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI implements CommandLineRunner {

    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final ConnectionService connectionService;

    private User loggedInUser = null;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleUI(
            UserService userService,
            PostService postService,
            LikeService likeService,
            CommentService commentService,
            ConnectionService connectionService
    ) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.connectionService = connectionService;
    }

    @Override
    public void run(String... args) {
        start();
    }

    // MAIN LOOP
    private void start() {
        while (true) {
            try {
                if (loggedInUser == null) {
                    authMenu();
                } else {
                    mainMenu();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // AUTH MENU
    private void authMenu() {
        System.out.println("\n--- RevConnect ---");
        System.out.println("1. Register");
        System.out.println("2. Login");

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> register();
            case 2 -> login();
            default -> System.out.println("Invalid choice");
        }
    }

    // REGISTER
    private void register() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Account Type (PERSONAL / CREATOR / BUSINESS): ");
        AccountType type = AccountType.valueOf(sc.nextLine().trim().toUpperCase());

        userService.register(email, password, type);
        System.out.println("Registered successfully");
    }

    //LOGIN
    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        loggedInUser = userService.login(email, password);
        System.out.println("Logged in as " + loggedInUser.getEmail());
    }

    // MAIN MENU
    private void mainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create Post");
        System.out.println("2. View My Posts");
        System.out.println("3. Like Post");
        System.out.println("4. Unlike Post");
        System.out.println("5. Delete Post");
        System.out.println("6. Add Comment");
        System.out.println("7. View Comments");
        System.out.println("8. Delete Comment");
        System.out.println("9. Send Connection Request");
        System.out.println("10. View Pending Requests");
        System.out.println("11. Accept Request");
        System.out.println("12. Reject Request");
        System.out.println("13. View Connections");
        System.out.println("14. Logout");

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> createPost();
            case 2 -> viewMyPosts();
            case 3 -> likePost();
            case 4 -> unlikePost();
            case 5 -> deletePost();
            case 6 -> addComment();
            case 7 -> viewComments();
            case 8 -> deleteComment();
            case 9 -> sendRequest();
            case 10 -> viewPendingRequests();
            case 11 -> acceptRequest();
            case 12 -> rejectRequest();
            case 13 -> viewConnections();
            case 14 -> logout();
            default -> System.out.println("Invalid choice");
        }
    }

    // POSTS
    private void createPost() {
        System.out.print("Content: ");
        postService.createPost(loggedInUser.getId(), sc.nextLine());
        System.out.println("Post created");
    }

    //VIEW POSTS
    private void viewMyPosts() {
        List<Post> posts = postService.getMyPosts(loggedInUser.getId());

        if (posts.isEmpty()) {
            System.out.println("No posts found");
            return;
        }

        posts.forEach(p -> {
            System.out.println("----------------");
            System.out.println("Post ID: " + p.getId());
            System.out.println(p.getContent());
            System.out.println("----------------");
        });
    }

    //DELETE POST
    private void deletePost() {
        viewMyPosts();
        System.out.print("Post ID: ");
        postService.deletePost(Long.parseLong(sc.nextLine()), loggedInUser.getId());
        System.out.println("Post deleted");
    }

    // LIKES
    private void likePost() {
        System.out.print("Post ID: ");
        likeService.likePost(loggedInUser.getId(), Long.parseLong(sc.nextLine()));
        System.out.println("Post liked");
    }

    //UNLIKE POST
    private void unlikePost() {
        System.out.print("Post ID: ");
        likeService.unlikePost(loggedInUser.getId(), Long.parseLong(sc.nextLine()));
        System.out.println("Post unliked");
    }

    // COMMENTS
    private void addComment() {
        System.out.print("Post ID: ");
        Long postId = Long.parseLong(sc.nextLine());

        System.out.print("Comment: ");
        commentService.addComment(loggedInUser.getId(), postId, sc.nextLine());
        System.out.println("Comment added");
    }

    //VIEW COMMENTS
    private void viewComments() {
        System.out.print("Post ID: ");
        List<Comment> comments =
                commentService.getCommentsByPost(Long.parseLong(sc.nextLine()));

        if (comments.isEmpty()) {
            System.out.println("No comments");
            return;
        }

        comments.forEach(c -> {
            System.out.println("----------------");
            System.out.println(c.getText());
            System.out.println("----------------");
        });
    }

    //DELETE COMMENT
    private void deleteComment() {
        System.out.print("Comment ID: ");
        commentService.deleteComment(Long.parseLong(sc.nextLine()), loggedInUser.getId());
        System.out.println("Comment deleted");
    }

    // SEND CONNECTION REQUEST
    private void sendRequest() {
        System.out.print("User ID to connect: ");
        connectionService.sendRequest(loggedInUser.getId(), Long.parseLong(sc.nextLine()));
        System.out.println("Request sent");
    }

    //VIEW PENDING CONNECTION REQUESTS
    private void viewPendingRequests() {
        List<Connection> requests =
                connectionService.getPendingRequests(loggedInUser.getId());

        if (requests.isEmpty()) {
            System.out.println("No pending requests");
            return;
        }

        requests.forEach(r ->
                System.out.println("Request ID: " + r.getId())
        );
    }

    //ACCEPT CONNECTION REQUESTS
    private void acceptRequest() {
        System.out.print("Request ID: ");
        connectionService.acceptRequest(Long.parseLong(sc.nextLine()));
        System.out.println("Request accepted");
    }

    //REJECT CONNECTION REQUESTS
    private void rejectRequest() {
        System.out.print("Request ID: ");
        connectionService.rejectRequest(Long.parseLong(sc.nextLine()));
        System.out.println("Request rejected");
    }

    //VIEW CONNECTIONS
    private void viewConnections() {
        List<Connection> connections =
                connectionService.getConnections(loggedInUser.getId());

        if (connections.isEmpty()) {
            System.out.println("No connections");
            return;
        }

        connections.forEach(c ->
                System.out.println("Connected User ID: " +
                        (c.getSenderId().equals(loggedInUser.getId())
                                ? c.getReceiverId()
                                : c.getSenderId()))
        );
    }

    //LOGOUT
    private void logout() {
        loggedInUser = null;
        System.out.println("Logged out");
    }
}
