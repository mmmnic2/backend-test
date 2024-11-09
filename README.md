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
