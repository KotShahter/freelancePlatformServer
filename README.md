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

### User Capabilities
- Place up to three orders using telegram bot
- Register inside the telegram bot
- Cancel order

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

## Getting started 

### Prerequisites
- Java 17+
- Maven or Gradle
- IDE (IntelliJ IDEA recommended)
- Telegram account

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/KotShahte/freelancePlatformServer.git
   cd tutor-platform
   ```
2. Create your own Telegram bot using @BotFather and get its unique API key
Optionally include autocomplete for commands

```
/setcommands
start - Start the bot
register - Registers the user
order - Place an order 
cancel - Cancel current order

```
   
3. Configure your database:
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. Insert API key to path variables

5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   # or
   ./gradlew bootRun
   ```
6. Open `http://localhost:8080` in your browser or deploy this somewhere else

### Default Credentials
| Role   | Username | Password |
|--------|----------|----------|
| Admin  | `admin`  | `123`    |

> Users can be created via the Admin panel with custom passwords.

## Usage Guide

### Admin Workflow
1. Log in as `admin`
2. Navigate to **Users Management** to create/delete accounts
3. Switch to **Orders Management** to monitor & delete requests

### Tutor Workflow
1. Log in as `tutor`
2. View **Open Orders** (top section) → Click "Accept" to claim
3. View **Closed Orders** (bottom section) → Track completed work
4. Orders are sorted by newest first within each status group

### User Workflow
1. Use your telegram account to use the bot
2. Register
3. Create an order

##  Project Structure
```
src/main/
── kotlin/com/withABow/freelancePlatform/
│   ├── entities/
│   │   ├── Order.kt
│   │   └── User.kt
│   ├── repos/
│   │   ├── OrderRepository.kt
│   │   └── UserRepository.kt
│   ├── services/
│   │   ├── OrderService.kt
│   │   └── UserService.kt
│   ├── telegram/
│   │   └── TelegramBot.kt
│   ├── CustomUserDetailsService.kt
│   ├── FreelancePlatformApplication.kt
│   ├── LoginController.kt
│   ├── MainController.kt
│   └── SecurityConfig.kt
└── resources/
    ├── static/
    │   ├── admin.html
    │   ├── app.js
    │   ├── index.html
    │   ├── logo.png
    │   ├── styles.css
    │   └── tutor.html
    ├── templates/
    └── application.yaml
```


## 🚧 Future Improvements
- [ ] Pagination & advanced search filters
- [ ] Telegram notifications for order acceptance
- [ ] Split the app on microservises
- [ ] CI / CD implementation

## License
This project is open-source under the [MIT License](LICENSE).

---
Built by Evgenii Lapenko / @WithABow
