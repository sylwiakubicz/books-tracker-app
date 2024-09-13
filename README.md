# Pandary API

## Project Description
The backend of the Pandary application is powered by Spring Boot, providing a robust and scalable API that supports full CRUD (Create, Read, Update, Delete) operations for all database tables. The database structure consists of several interconnected tables, including users, books, authors, genres, statuses, and user roles, allowing for comprehensive management of a personal library. The backend manages all user interactions such as retrieving book details, updating reading statuses, and administering user accounts. It serves as the backbone of the application, efficiently handling all the business logic and data persistence while interacting with the frontend to deliver a seamless experience to both administrators and users.

Link to the corresponding [frontend repository](https://github.com/sylwiakubicz/books-tracker-front/tree/master).

## App Schema
<p align="center"> 
  <img src="https://github.com/user-attachments/assets/f284fc60-b2c3-4d44-943c-0c10c7dc6c0c" alt="App Schema" style="display: flex; justify-content: center;">
</p>

## Database Structure
![Database Structure](https://github.com/user-attachments/assets/7c204d5a-d0b5-4486-97c7-77c705ab348d)

## Technologies Used
- Java
- Gradle
- Spring Boot (Web, JPA, Security, Validation)
- MySQL, Liquibase
- Google API Client (Drive API)
- Spring Session (Core, Redis)
- Railway.app

## What I've Learned
During this project, I gained experience with various technologies and frameworks, improving my skills in backend development, security, database management, and API integration. Here are some key learnings:

- Learned the principles of object-oriented programming in **Java**.
- Gained experience in configuring projects, managing dependencies, and builds using **Gradle**.
- Implemented the **Model-View-Controller (MVC)** architecture in Spring Boot by creating models, services, repositories, and controllers to improve the separation of application logic and organization.
- Used **JPA** for database management, including handling entity relationships and queries.
- Configured **Spring Security** for authorization, role-based access control, and session management.
- Applied **input validation** to ensure data integrity and security within the application.
- Created **REST controllers** to handle **HTTP requests** and map endpoints for the application's functionality.
- Developed dynamic database queries using **Spring Specifications**, allowing flexible searching in the application based on runtime criteria.
- Utilized **Springdoc annotations** to automatically generate clear and understandable API documentation, simplifying API testing and integration.
- Configured web application security using **Spring Security**, implementing login, logout, and secure password hashing with **BCrypt**, and configured CORS to manage communication between frontend and backend across different domains and HTTP methods.
- Implemented caching mechanisms using **Spring Cache and ConcurrentMapCacheManager**, which improved the application's performance by caching frequently accessed data such as random books.
- Managed relational databases, created schemas, and handled database migrations using **Liquibase**, ensuring consistency across different environments.
- Integrated **Google Drive API** for file uploads, handling authentication using service keys, and managing file operations through Google Client libraries, including GoogleCredential and Drive.
- Managed user sessions in a distributed environment using **Redis**, enhancing the scalability of web applications.
- Implemented **pagination** to manage and display data in a user-friendly way, enhancing the user experience when dealing with large datasets.
- Deployed the database and backend repository using **Railway.app**, ensuring smooth hosting and continuous integration for the backend services.

## How to Run the Application and Load Test Data

### Method 1: Easiest way to test the application
If you want to test by the frontend Angular application that interacts with the API, you can use a provided link to an online hosted version of the frontend app.

1. [Click the link](https://pandary.vercel.app/) shared by the project maintainers.
2. The application will open in your browser, and you can test it without any additional steps.

You can use the following default credentials to test the application:

- **Admin user:**
  - **Username:** `adminUser`
  - **Password:** `admin`
  
- **Regular user:**
  - **Username:** `regularUser`
  - **Password:** `user`

> **NOTE:**  
> This method works only if the application is hosted online and the link is provided by the developers.

### Method 2: Using an existing hosted backend database

1. **Clone the repository from GitHub**

    ```bash
    git clone https://github.com/sylwiakubicz/books-tracker-app.git
    ```

2. **Navigate to the project directory**

    ```bash
    cd books-tracker-app
    ```

3. **Build the project**

    ```bash
    ./gradlew build
    ```

4. **Start the Spring Boot application**

    ```bash
    ./gradlew bootRun
    ```

### Method 3: Creating and using your own local database

1. **Set up MySQL database**

   Install MySQL and create a new database for the application. For example, you can create a database named `books_db`.

2. **Set environment variables**

   - `MYSQLUSER`: Your MySQL username.
   - `MYSQL_ROOT_PASSWORT`: Your MySQL root password or database user password.
   - `DATABASE_URL`: URL of your local MySQL database.

3. **Run the application**
   
   Liquibase is integrated with the Spring Boot project and will automatically generate all the necessary tables and insert static test data after you start the application. Simply run:

   ```bash
   ./gradlew bootRun
