---

# Course Management RESTful API

## Project Overview
This project is a **Course Management System** built on a **Client-Side Rendering (CSR) architecture pattern** using **Spring Boot** as the backend framework and **PostgreSQL** as the database. The API provides full **CRUD (Create, Read, Update, Delete)** operations for managing courses.

---

## Technologies Used
- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL
- **Architecture**: CSR (Client-Side Rendering)
- **API Format**: RESTful

---

## Features
The Course Management API supports the following CRUD operations:
- **Create**: Add a new course.
- **Read**: Fetch all courses or a specific course by ID.
- **Update**: Modify details of an existing course.
- **Delete**: Remove a course from the system.

---

## API Endpoints
### 1. **Create a Course**
   - **Method**: `POST`
   - **Endpoint**: `/api/courses`
   - **Description**: Adds a new course to the database.
   - **Request Body** (JSON):
     ```json
     {
       "id": "Course ID",
       "title": "Course Title"
       "description": "Course Description",
     }
     ```
   - **Response**: Returns the created course object with a unique ID.

### 2. **Read All Courses**
   - **Method**: `GET`
   - **Endpoint**: `/api/courses`
   - **Description**: Retrieves a list of all available courses.
   - **Response**: Array of course objects.

### 3. **Read a Course by ID**
   - **Method**: `GET`
   - **Endpoint**: `/api/courses/{id}`
   - **Description**: Retrieves details of a specific course by ID.
   - **Response**: Course object with the provided ID.

### 4. **Update a Course**
   - **Method**: `PUT`
   - **Endpoint**: `/api/courses/{id}`
   - **Description**: Updates an existing course.
   - **Request Body** (JSON):
     ```json
     {
       "Id": "Previous ID",
       "description": "Updated Description",
       "duration": "Updated Duration"
     }
     ```
   - **Response**: Returns the updated course object.

### 5. **Delete a Course**
   - **Method**: `DELETE`
   - **Endpoint**: `/api/courses/{id}`
   - **Description**: Deletes a course from the database by ID.
   - **Response**: Success message confirming the deletion.

---

## Database Schema
The PostgreSQL database has a `courses` table with the following structure:
- **id** (Primary Key, auto-generated)
- **name** (String)
- **description** (String)
- **duration** (String)

---

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ex2uply/course-management-api.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd course-management-api
   ```

3. **Configure PostgreSQL**:
   - Make sure you have PostgreSQL installed and running.
   - Create a database named `course_management`.
   - Update the `application.properties` file with your PostgreSQL credentials.

4. **Build and Run the Project**:
   - Use Maven to build and run the project:
     ```bash
     mvn spring-boot:run
     ```

5. **Test the API**:
   - You can use tools like **Postman** or **cURL** to interact with the API.

---

## License
This project is licensed under the MIT License.

---

## Author
[Aditya Kumar] - Developer

