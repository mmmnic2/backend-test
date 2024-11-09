# BACKEND TEST PROJECT

This Spring Boot application uses Java 17 and MySQL as the database. It demonstrates basic functionality with a RESTful API and may include features such as CRUD operations (Book).

### Deploy Swagger link: 
https://backend-test-5fok.onrender.com/swagger-ui/index.html#/

## Requirements

Before you can run this application, you will need to have the following installed:

- **JDK**: Version 17 
- **Maven**: Version 3.8.x or higher
- **Database**: MySQL

## Endpoint APIs

```javascript
/**
 * @route POST /books
 * @description create and update book. If requestBody has field bookId then api will be update.
 * @body {bookId , title, author, publishedDate, isbn, price}
 * @access Public
 * /
```

```javascript
/**
 * @route GET /books
 * @description get all books.
 * @access Public
 * /
```

```javascript
/**
 * @route GET /books/{id}
 * @description get a book by id.
 * @PathVariable id
 * @access Public
 * /
```

```javascript
/**
 * @route DELETE /books/{id}
 * @description delete a book by id.
 * @PathVariable id
 * @access Public
 * /
```

###  Completion time: 5h
