package com.projects.spring.restapidemo.services;

import com.projects.spring.restapidemo.dto.CourseRequest;
import com.projects.spring.restapidemo.dto.CourseResponse;
import com.projects.spring.restapidemo.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CourseService {
    
    // Basic CRUD operations
    List<CourseResponse> getAllCourses();
    Page<CourseResponse> getAllCourses(Pageable pageable);
    CourseResponse getCourseById(Long id);
    CourseResponse createCourse(CourseRequest courseRequest);
    CourseResponse updateCourse(Long id, CourseRequest courseRequest);
    void deleteCourse(Long id);
    
    // Search and filter operations
    List<CourseResponse> searchCourses(String keyword);
    List<CourseResponse> getCoursesByInstructor(String instructor);
    List<CourseResponse> getCoursesByLevel(Course.CourseLevel level);
    List<CourseResponse> getCoursesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<CourseResponse> getActiveCourses();
    Page<CourseResponse> getActiveCourses(Pageable pageable);
    
    // Statistics
    long getCourseCount();
    long getCourseCountByInstructor(String instructor);
}
