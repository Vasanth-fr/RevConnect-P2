# RevConnect – Phase 2 (Spring Boot + Spring Data JPA)

RevConnect is a **console-based social networking application** developed as part of Phase-2 of the project.  
This phase focuses on **migrating from plain Java + JDBC to Spring Boot with Spring Data JPA**, while enforcing **clean architecture and separation of concerns**.

---

## 🚀 Tech Stack

- **Java** 17+
- **Spring Boot** 4.x
- **Spring Data JPA**
- **Hibernate ORM**
- **MySQL**
- **Maven**
- **HikariCP**
- **Lombok**

---

## 🧱 Architecture

The application follows a **layered architecture**:  Entity → Repository → Service → Console UI


### Key Design Rules Followed
- ❌ No JDBC (`Connection`, `PreparedStatement`, `ResultSet`)
- ❌ No `System.out.println` in Service layer
- ❌ No DB logic outside repositories
- ✅ Business rules handled in Service layer
- ✅ Exceptions thrown from Service, handled in UI
- ✅ Spring Dependency Injection (Constructor Injection)
- ✅ Console-based UI using `CommandLineRunner`

---

## 📦 Modules & Responsibilities

### 🧩 Entity Layer
- JPA entities mapped using annotations
- Enums used for domain safety (e.g., `AccountType`, `ConnectionStatus`)

### 🗄 Repository Layer
- Uses `JpaRepository`
- No manual SQL
- Derived query methods (`findBy`, `existsBy`, `deleteBy`)
- Handles all DB interactions

### ⚙️ Service Layer
- Business logic and validations
- Throws exceptions instead of printing
- No DB or UI code

### 🖥 Console UI Layer
- Handles user input/output
- Catches and displays service exceptions
- Implemented using `CommandLineRunner`

---

## ✨ Features

### 👤 User
- Register
- Login
- Logout

### 📝 Posts
- Create post
- View own posts
- Delete post

### ❤️ Likes
- Like a post
- Unlike a post
- Prevent duplicate likes

### 💬 Comments
- Add comment
- View comments on a post
- Delete own comment

### 🤝 Connections
- Send connection request
- View pending requests
- Accept / reject request
- View accepted connections

---

🧪 Sample Console Flow:

--- RevConnect ---
1. Register
2. Login

--- Main Menu ---
1. Create Post
2. View My Posts
3. Like Post
4. Unlike Post
5. Delete Post
6. Add Comment
7. View Comments
8. Delete Comment
9. Send Connection Request
10. View Pending Requests
11. Accept Request
12. Reject Request
13. View Connections
14. Logout





