package com.projects.spring.restapidemo.repo;

import com.projects.spring.restapidemo.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    
    // Find courses by instructor
    List<Course> findByInstructor(String instructor);
    
    // Find courses by level
    List<Course> findByLevel(Course.CourseLevel level);
    
    // Find active courses
    List<Course> findByIsActiveTrue();
    
    // Find courses by price range
    List<Course> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Search courses by title or description
    @Query("SELECT c FROM Course c WHERE " +
           "LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Course> searchCourses(@Param("keyword") String keyword);
    
    // Find courses with pagination
    Page<Course> findByIsActiveTrue(Pageable pageable);
    
    // Find courses by instructor with pagination
    Page<Course> findByInstructorAndIsActiveTrue(String instructor, Pageable pageable);
    
    // Find courses by level with pagination
    Page<Course> findByLevelAndIsActiveTrue(Course.CourseLevel level, Pageable pageable);
    
    // Count courses by instructor
    long countByInstructor(String instructor);
    
    // Find courses with price less than specified amount
    List<Course> findByPriceLessThanAndIsActiveTrue(BigDecimal price);
}
