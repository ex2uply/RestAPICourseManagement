# Course Management API

A comprehensive REST API for managing online courses built with Spring Boot, featuring advanced security, validation, pagination, and comprehensive documentation.

## 🚀 Features

- **CRUD Operations**: Complete Create, Read, Update, Delete operations for courses
- **Advanced Search**: Search courses by title, description, instructor, level, and price range
- **Pagination & Sorting**: Efficient data retrieval with pagination and sorting capabilities
- **Input Validation**: Comprehensive validation using Bean Validation annotations
- **Security**: Spring Security with role-based access control
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Error Handling**: Global exception handling with meaningful error responses
- **Logging**: Comprehensive logging throughout the application
- **Database**: PostgreSQL with JPA/Hibernate
- **Testing**: Unit and integration tests

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot 3.3.3**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Lombok**
- **Swagger/OpenAPI 3**
- **Maven**

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd RestAPICourseManagement
```

### 2. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE course_management;
```

### 3. Environment Configuration

Create a `.env` file or set environment variables:

```bash
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/course_management
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Security Configuration
ADMIN_USERNAME=admin
ADMIN_PASSWORD=admin123
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# Application Configuration
DDL_AUTO=update
SHOW_SQL=false
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## 📚 API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/api-docs

## 🔐 Authentication

The API uses Spring Security with basic authentication:

- **Username**: admin (or set via `ADMIN_USERNAME`)
- **Password**: admin123 (or set via `ADMIN_PASSWORD`)

### Public Endpoints (No Authentication Required)

- `GET /api/v1/courses/health` - Health check
- `GET /api/v1/courses` - Get all courses (with pagination)
- `GET /api/v1/courses/{id}` - Get course by ID
- `GET /api/v1/courses/search` - Search courses
- `GET /api/v1/courses/instructor/{instructor}` - Get courses by instructor
- `GET /api/v1/courses/level/{level}` - Get courses by level
- `GET /api/v1/courses/price-range` - Get courses by price range
- `GET /api/v1/courses/active` - Get active courses
- `GET /api/v1/courses/stats/**` - Get statistics

### Protected Endpoints (Admin Role Required)

- `POST /api/v1/courses` - Create new course
- `PUT /api/v1/courses/{id}` - Update course
- `DELETE /api/v1/courses/{id}` - Delete course

## 📖 API Usage Examples

### Get All Courses with Pagination

```bash
curl -X GET "http://localhost:8081/api/v1/courses?page=0&size=10&sortBy=title&sortDir=asc"
```

### Create a New Course

```bash
curl -X POST "http://localhost:8081/api/v1/courses" \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "title": "Advanced Java Programming",
    "description": "Learn advanced Java concepts including multithreading, design patterns, and performance optimization",
    "price": 299.99,
    "instructor": "Dr. Jane Smith",
    "durationHours": 60,
    "level": "ADVANCED",
    "isActive": true
  }'
```

### Search Courses

```bash
curl -X GET "http://localhost:8081/api/v1/courses/search?keyword=java"
```

### Get Courses by Price Range

```bash
curl -X GET "http://localhost:8081/api/v1/courses/price-range?minPrice=50&maxPrice=200"
```

## 🗄️ Database Schema

### Course Entity

| Field          | Type          | Constraints                 | Description                      |
| -------------- | ------------- | --------------------------- | -------------------------------- |
| id             | BIGINT        | PRIMARY KEY, AUTO_INCREMENT | Unique identifier                |
| title          | VARCHAR(100)  | NOT NULL, 3-100 chars       | Course title                     |
| description    | VARCHAR(500)  | NOT NULL, 10-500 chars      | Course description               |
| price          | DECIMAL(12,2) | NOT NULL, > 0               | Course price                     |
| instructor     | VARCHAR(50)   | NOT NULL, 2-50 chars        | Instructor name                  |
| duration_hours | INTEGER       | NOT NULL, 1-1000            | Course duration in hours         |
| level          | ENUM          | NOT NULL                    | BEGINNER, INTERMEDIATE, ADVANCED |
| is_active      | BOOLEAN       | DEFAULT true                | Course status                    |
| created_at     | TIMESTAMP     | AUTO                        | Creation timestamp               |
| updated_at     | TIMESTAMP     | AUTO                        | Last update timestamp            |

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

Run tests with coverage:

```bash
mvn test jacoco:report
```

## 📊 Monitoring

The application includes Spring Boot Actuator for monitoring:

- **Health Check**: http://localhost:8081/actuator/health
- **Application Info**: http://localhost:8081/actuator/info
- **Metrics**: http://localhost:8081/actuator/metrics

## 🚀 Deployment

### Using Docker

1. Build the application:

```bash
mvn clean package
```

2. The Dockerfile is already included in the project:

```dockerfile
FROM openjdk:21-jdk-slim
# ... (see Dockerfile for complete configuration)
COPY target/course-management-api-1.3.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

3. Build and run:

```bash
docker build -t course-management-api .
docker run -p 8081:8081 course-management-api
```

### Environment Variables for Production

```bash
export DB_URL=jdbc:postgresql://your-db-host:5432/course_management
export DB_USERNAME=your_prod_username
export DB_PASSWORD=your_secure_password
export ADMIN_USERNAME=your_admin_username
export ADMIN_PASSWORD=your_secure_admin_password
export JWT_SECRET=your-very-secure-secret-key
export DDL_AUTO=validate
export SHOW_SQL=false
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/your-repo/issues) page
2. Create a new issue with detailed information
3. Contact the development team

## 🔄 Version History

- **v1.0.0** - Initial release with basic CRUD operations
- **v1.1.0** - Added search, filtering, and pagination
- **v1.2.0** - Enhanced security and validation
- **v1.3.0** - Added comprehensive documentation and testing

---

**Happy Coding! 🎉**
