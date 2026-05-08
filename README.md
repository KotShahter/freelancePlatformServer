# tutoR. - Freelance Tutoring Platform

A system, that consists of a web application and a telegram bot for managing tutoring requests, built with **Spring Boot (Kotlin)**, **Kotlin telegram bot API wrapper** and a modern **Vanilla JS frontend (Frontend was vibecoded)**. Features distinct creating orders from the telegram bot, managing dashboards for Administrators and Tutors, session-based authentication, and a clean, responsive UI.

## Features

### Role-Based Access Control
- **Admin Dashboard:** Full user & order management
- **Tutor Dashboard:** Browse, filter, and accept tutoring requests
- **Secure Authentication:** Session-based login with Spring Security & BCrypt

### Admin Capabilities
- Create users with custom or default passwords
- Delete users & orders
- View all orders with full details & timestamps
- Strict endpoint protection

### Tutor Capabilities
- View all orders in a clean horizontal layout
- Accept available orders with one click
- View detailed order information
- Visual distinction between available and completed tasks

### UI/UX
- Responsive design (mobile & desktop)
- Modern card-based layout with status badges
- Clear separation of Open vs. Closed orders with gradient dividers
- Session persistence across page refreshes (`JSESSIONID`)

## 🛠 Tech Stack

| Layer       | Technologies                          |
|-------------|---------------------------------------|
| **Backend** | Kotlin, Spring Boot 3, Spring Security, Spring Data JPA |
| **Database**| PostgreSQL             |
| **Security**    | Session-based, Spring Security  |
| **Build**   | Gradle                |

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
└── resources/static/
    ├── index.html      # Login page
    ├── admin.html      # Admin dashboard
    ├── tutor.html      # Tutor dashboard
    ├── styles.css      # Global styles
    ── app.js          # Frontend logic & API calls
```

## 🔌 API Endpoints
| Method | Endpoint          | Access      | Description               |
|--------|-------------------|-------------|---------------------------|
| `POST` | `/api/login`      | Public      | Authenticate user         |
| `GET`  | `/api/users`      | Admin/Tutor | List all users            |
| `POST` | `/api/users/**`   | Admin       | Create user (role-specific)|
| `DELETE`| `/api/users`     | Admin       | Delete user               |
| `GET`  | `/api/orders`     | Admin/Tutor | List all orders           |
| `PATCH`| `/api/orders`     | Tutor       | Accept order              |
| `DELETE`| `/api/orders`    | Admin       | Delete order              |

## 📸 Screenshots
*(Замените эти placeholder'ы на реальные скриншоты вашего проекта)*
| Login | Admin Dashboard | Tutor Dashboard |
|:---:|:---:|:---:|
| ![Login](screenshots/login.png) | ![Admin](screenshots/admin.png) | ![Tutor](screenshots/tutor.png) |

## 🚧 Future Improvements
- [ ] JWT authentication for stateless API
- [ ] File attachments for orders (PDF/DOCX)
- [ ] Real-time notifications (WebSocket)
- [ ] Pagination & advanced search filters
- [ ] Email notifications for order acceptance
- [ ] Unit & Integration tests (JUnit 5, MockMvc)

## 📄 License
This project is open-source under the [MIT License](LICENSE).

---
Built by Evgenii Lapenko
```

### 📌 Как использовать:
1. Создайте файл `README.md` в корне репозитория
2. Скопируйте весь код выше
3. Замените `YOUR_USERNAME`, `[Your Name/Handle]` и placeholder'ы для скриншотов
4. Залейте на GitHub — всё отрендерится автоматически

Если хотите, могу добавить секцию с **Dockerfile** или **GitHub Actions CI/CD** для автоматической сборки. Просто скажите! 🚀
