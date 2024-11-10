# BACKEND TEST PROJECT

This Spring Boot application uses Java 17 and MySQL as the database. It demonstrates basic functionality with a RESTful API and may include features such as CRUD operations (Book).

### Deploy Swagger link: 
https://backend-test-5fok.onrender.com/swagger-ui/index.html#/

## Requirements

Before you can run this application, you will need to have the following installed:

- **JDK**: Version 17 
- **Maven**: Version 3.8.x or higher
- **Database**: MySQL

## Steps to Set Up and Run the Application

**1. Clone the repository**
```javascript
git clone https://github.com/mmmnic2/backend-test.git
cd <project-directory>
```

**2. Configure Application Properties** 

In the src/main/resources/application.properties file, comment out the deployment database configuration and use the MySQL properties Local settings. Then, update the MySQL username and password fields with your credentials:
```javascript
# MySQL properties Local
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/backend_test?createDatabaseIfNotExist=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

# deploy database
# spring.jpa.hibernate.ddl-auto=update
# spring.datasource.url=${JDBC_DATABASE_URL:}?createDatabaseIfNotExist=true
# spring.datasource.username=${JDBC_USERNAME:}
# spring.datasource.password=${JDBC_PASSWORD:}
# spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
```

**3. Build the Project** 

Run the following Maven command to build the project and download dependencies:
```javascript
mvn clean install
```

**4. Run the Application** 

Once the build is successful, start the Spring Boot application:
```javascript
mvn spring-boot:run
```

**5. Access the Application** 

The application runs on port 8080 by default. You can access Swagger UI to test and interact with the API using the following URL:
- Swagger UI (for easily testing the API):
```javascript
http://localhost:8080/swagger-ui/index.html#/
```

## Endpoint APIs

```javascript
/**
 * @route POST /api/v1/books
 * @description create and update the book. If requestBody has field bookId then API will be updated.
 * @body {bookId , title, author, publishedDate, isbn, price}
 * @access Public
 * /
```

```javascript
/**
 * @route GET /api/v1/books
 * @description get all books.
 * @access Public
 * /
```
```javascript
/**
 * @route GET /api/v1/books-pagination
 * @description Retrieves a paginated list of books.
 * @param page, size
 * @access Public
 * /
```

```javascript
/**
 * @route GET /api/v1/books/{id}
 * @description get a book by id.
 * @PathVariable id
 * @access Public
 * /
```

```javascript
/**
 * @route DELETE /api/v1/books/{id}
 * @description delete a book by id.
 * @PathVariable id
 * @access Public
 * /
```

###  Completion time: 8h
