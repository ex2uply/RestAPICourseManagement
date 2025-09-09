package com.projects.spring.restapidemo.controller;

import com.projects.spring.restapidemo.dto.ApiResponse;
import com.projects.spring.restapidemo.dto.CourseRequest;
import com.projects.spring.restapidemo.dto.CourseResponse;
import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Course Management", description = "APIs for managing courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/health")
    @Operation(summary = "Health check endpoint")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Course Management API is running", "API is healthy"));
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve all courses with optional pagination and sorting")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved courses"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Page<CourseResponse>>> getAllCourses(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Fetching all courses - page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CourseResponse> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(ApiResponse.success(courses, "Courses retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve a specific course by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Course found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Course not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(
            @Parameter(description = "Course ID") @PathVariable Long id) {
        
        log.info("Fetching course with id: {}", id);
        CourseResponse course = courseService.getCourseById(id);
        return ResponseEntity.ok(ApiResponse.success(course, "Course retrieved successfully"));
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Create a new course with the provided details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Course created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Parameter(description = "Course details") @Valid @RequestBody CourseRequest courseRequest) {
        
        log.info("Creating new course: {}", courseRequest.getTitle());
        CourseResponse course = courseService.createCourse(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(course, "Course created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update an existing course with the provided details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Course not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @Parameter(description = "Course ID") @PathVariable Long id,
            @Parameter(description = "Updated course details") @Valid @RequestBody CourseRequest courseRequest) {
        
        log.info("Updating course with id: {}", id);
        CourseResponse course = courseService.updateCourse(id, courseRequest);
        return ResponseEntity.ok(ApiResponse.success(course, "Course updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Course deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Course not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Void>> deleteCourse(
            @Parameter(description = "Course ID") @PathVariable Long id) {
        
        log.info("Deleting course with id: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Course deleted successfully"));
    }

    @GetMapping("/search")
    @Operation(summary = "Search courses", description = "Search courses by keyword in title or description")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchCourses(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        
        log.info("Searching courses with keyword: {}", keyword);
        List<CourseResponse> courses = courseService.searchCourses(keyword);
        return ResponseEntity.ok(ApiResponse.success(courses, "Search completed successfully"));
    }

    @GetMapping("/instructor/{instructor}")
    @Operation(summary = "Get courses by instructor", description = "Retrieve all courses taught by a specific instructor")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByInstructor(
            @Parameter(description = "Instructor name") @PathVariable String instructor) {
        
        log.info("Fetching courses by instructor: {}", instructor);
        List<CourseResponse> courses = courseService.getCoursesByInstructor(instructor);
        return ResponseEntity.ok(ApiResponse.success(courses, "Courses retrieved successfully"));
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Get courses by level", description = "Retrieve all courses of a specific level")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByLevel(
            @Parameter(description = "Course level") @PathVariable Course.CourseLevel level) {
        
        log.info("Fetching courses by level: {}", level);
        List<CourseResponse> courses = courseService.getCoursesByLevel(level);
        return ResponseEntity.ok(ApiResponse.success(courses, "Courses retrieved successfully"));
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get courses by price range", description = "Retrieve courses within a specific price range")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        
        log.info("Fetching courses by price range: {} - {}", minPrice, maxPrice);
        List<CourseResponse> courses = courseService.getCoursesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(ApiResponse.success(courses, "Courses retrieved successfully"));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active courses", description = "Retrieve all active courses with optional pagination")
    public ResponseEntity<ApiResponse<Page<CourseResponse>>> getActiveCourses(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        log.info("Fetching active courses - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> courses = courseService.getActiveCourses(pageable);
        return ResponseEntity.ok(ApiResponse.success(courses, "Active courses retrieved successfully"));
    }

    @GetMapping("/stats/count")
    @Operation(summary = "Get course count", description = "Get total number of courses")
    public ResponseEntity<ApiResponse<Long>> getCourseCount() {
        log.info("Getting total course count");
        long count = courseService.getCourseCount();
        return ResponseEntity.ok(ApiResponse.success(count, "Course count retrieved successfully"));
    }

    @GetMapping("/stats/instructor/{instructor}/count")
    @Operation(summary = "Get course count by instructor", description = "Get number of courses taught by a specific instructor")
    public ResponseEntity<ApiResponse<Long>> getCourseCountByInstructor(
            @Parameter(description = "Instructor name") @PathVariable String instructor) {
        
        log.info("Getting course count for instructor: {}", instructor);
        long count = courseService.getCourseCountByInstructor(instructor);
        return ResponseEntity.ok(ApiResponse.success(count, "Course count retrieved successfully"));
    }
}
