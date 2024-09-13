[authors_data.csv](https://github.com/user-attachments/files/16998592/authors_data.csv)<h1>Pandary API</h1>

<h2>Project Description</h2>
The backend of the Pandary application is powered by Spring Boot, providing a robust and scalable API that supports full CRUD (Create, Read, Update, Delete) operations for all database tables. The database structure consists of several interconnected tables, including users, books, authors, genres, statuses, and user roles, allowing for comprehensive management of a personal library. The backend manages all user interactions such as retrieving book details, updating reading statuses, and administering user accounts. It serves as the backbone of the application, efficiently handling all the business logic and data persistence, while interacting with the frontend to deliver a seamless experience to both administrators and users. 
<br>
<br>
Link to the corresponding <a href="https://github.com/sylwiakubicz/books-tracker-front/tree/master">frontend repository</a>.

<h2>App schema</h2>
<p align="center"> 
  <img src="https://github.com/user-attachments/assets/f284fc60-b2c3-4d44-943c-0c10c7dc6c0c" style="display: flex; justify-contetnt: center;">
</p>
<h2>Database structure</h2>

![image](https://github.com/user-attachments/assets/7c204d5a-d0b5-4486-97c7-77c705ab348d)


<h2>Technologies used</h2>
<ul>
  <li>Java</li>
  <li>Gradle</li>
  <li>Spring Boot (Web, JPA, Security, Validation)</li>
  <li>MySQL, Liquibase</li>
  <li>Google API Client (Drive API)</li>
  <li>Spring Session (Core, Redis)</li>
  <li>Railway.app</li>
</ul>

<h2>What I've learned</h2>
During this project, I gained experience with various technologies and frameworks, improving my skills in backend development, security, database management, and API integration such as:
<ul>
  <li>Learned the principles of object-oriented programming in <strong>Java</strong>.</li>
  <li>Gained experience in configuring projects, managing dependencies and builds using <strong>Gradle</strong>.</li>
  <li>Implemented the <strong>Model-View-Controller (MVC)</strong> architecture in Spring Boot by creating models, services, repositories, and controllers to improve the separation of application logic and organization.</li>
  <li>Used <strong>JPA</strong> for database management, including handling entity relationships and queries.</li>
  <li>Configured <srtong>Spring Security</srtong> for authorization, role-based access control, and session management.</li>
  <li>Applied <strong>input validation</strong> to ensure data integrity and security within the application.</li>
  <li>Created <strong>REST controllers</strong> to handle <strong>HTTP requests</strong> and map endpoints for the application's functionality.</li>
  <li>Developed dynamic database queries using <strong>Spring Specifications</strong>, allowing flexible searching in the application based on runtime criteria.</li>
  <li>Utilized <strong>Springdoc annotations</strong> to automatically generate clear and understandable API documentation, simplifying API testing and integration.</li>
  <li>Configured web application security using <strong>Spring Security</strong>, implementing login, logout, and secure password hashing with <strong>BCrypt</strong>, and configured CORS to manage communication between frontend and backend across different domains and HTTP methods.</li>
  <li>Implemented caching mechanisms using <strong>Spring Cache and ConcurrentMapCacheManager</strong>, which improved the application's performance by caching frequently accessed data such as random books.</li>
  <li>Managed relational databases, created schemas, and handled database migrations using <strong>Liquibase</strong>, ensuring consistency across different environments.</li>
  <li>Integrated <strong>Google Drive API</strong> for file uploads, handling authentication using service keys, and managing file operations through Google Client libraries, including GoogleCredential and Drive.</li>
  <li>Learned to manage user sessions in a distributed environment using Redis, enhancing the scalability of web applications.</li>
  <li>Implemented <strong>pagination</strong> to manage and display data in a user-friendly way, enhancing the user experience when dealing with large datasets.</li>
  <li>Deployed the database and backend repository using <strong>Railway.app</strong>, ensuring smooth hosting and continuous integration for the backend services.</li>
</ul>

<h2>How to Run the Application and Load Test Data</h2> 

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
 
   Install MySQL and create a new database for the application. For example, you can create a database named books_db.

2. **Set enviroment variables**

   - `MYSQLUSER`: Your MySQL username.
   - `MYSQL_ROOT_PASSWORT`: Your MySQL root password or database user password.
   - `DATABASE_URL`: URL of your local MySQL database.

3. **Run the application**
   
   Liquibase is integrated with the Spring Boot project and will automatically generate all the necessary tables and insert static test data after you start the application. Simply run:

   ```bash
   ./gradlew bootRun
   ```
   
4. **Load additional data** (Optional)
   
  You can also load extra data into the books, authors, genres, and other linking tables by downloading the following CSV files and importing them into your MySQL database:




  You can now connect the frontend application or test the API endpoints using a tool like Postman.

<h2>Author</h2>
<p>Sylwia Kubicz</p>
