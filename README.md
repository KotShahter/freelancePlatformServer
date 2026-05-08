# tutoR. - Freelance Tutoring Platform

A system, that consists of a web application and a telegram bot for managing tutoring requests, built with **Spring Boot (Kotlin)**, **Kotlin telegram bot API wrapper** and a modern **Vanilla JS frontend (Frontend was vibecoded)**. Features distinct creating orders from the telegram bot, managing dashboards for Administrators and Tutors, session-based authentication, and a clean, responsive UI.

## Features

### Role-Based Access Control
- **Create Orders via Telegram bot** Convenient way for placing orders by users
- **Admin Dashboard:** Full user & order management
- **Tutor Dashboard:** Browse, and accept tutoring requests
- **Secure Authentication:** Session-based login with Spring Security & BCrypt

### Admin Capabilities
- Create users with custom or default passwords
- Delete users & orders
- View all orders with full details & timestamps

### Tutor Capabilities
- View all orders in a clean horizontal layout
- Accept available orders with one click
- View detailed order information
- Visual distinction between available and completed tasks

### UI/UX
- Responsive design
- Modern card-based layout with status badges
- Vibecoded

### 🛠 Tech Stack

| Layer       | Technologies                          |
|-------------|---------------------------------------|
| **Backend** | Kotlin, Spring Boot, Spring Security|
| **Database**| PostgreSQL, Spring Data JPA         |
| **Security**    | Spring Security  |
| **Integration** | Telegram API, Kotlin Telegram Bot |
| **Build**   | Gradle    |

##  Getting Started

### Prerequisites
- Java 17+
- Maven or Gradle
- IDE (IntelliJ IDEA recommended)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/tutor-platform.git
   cd tutor-platform
   ```
2. Configure database (optional, defaults to in-memory H2):
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   # or
   ./gradlew bootRun
   ```
4. Open `http://localhost:8080` in your browser.

### 🔑 Default Credentials
| Role   | Username | Password |
|--------|----------|----------|
| Admin  | `admin`  | `123`    |
| Tutor  | `tutor`  | `123`    |

> 💡 Users can be created via the Admin panel with custom passwords.

## 📖 Usage Guide

### Admin Workflow
1. Log in as `admin`
2. Navigate to **Users Management** to create/delete accounts
3. Switch to **Orders Management** to monitor & delete requests
4. All actions are protected by `@PreAuthorize` / Spring Security filters

### Tutor Workflow
1. Log in as `tutor`
2. View **Open Orders** (top section) → Click "Accept" to claim
3. View **Closed Orders** (bottom section) → Track completed work
4. Orders are sorted by newest first within each status group

##  Project Structure
```
src/main/
├── kotlin/com/withABow/freelancePlatform/
│   ├── SecurityConfig.kt
│   ├── CustomUserDetailsService.kt
│   ├── MainController.kt
│   ├── LoginController.kt
│   ├── services/
│   ├── repos/
│   └── entities/
|   
└── resources/static/
    ├── index.html      # Login page
    ├── admin.html      # Admin dashboard
    ├── tutor.html      # Tutor dashboard
    ├── styles.css      # Global styles
    ── app.js          # Frontend logic & API calls
```

## 🚧 Future Improvements
- [ ] Pagination & advanced search filters
- [ ] Telegram notifications for order acceptance
- [ ] Split the app on microservises

## 📄 License
This project is open-source under the [MIT License](LICENSE).

---
Built by Evgenii Lapenko
```
