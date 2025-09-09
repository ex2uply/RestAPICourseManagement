package com.projects.spring.restapidemo.services;

import com.projects.spring.restapidemo.dto.CourseRequest;
import com.projects.spring.restapidemo.dto.CourseResponse;
import com.projects.spring.restapidemo.exception.CourseNotFoundException;
import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServiceCourse implements CourseService {
    
    private final CourseRepo courseRepo;

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepo.findAll().stream()
                .map(CourseResponse::fromCourse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        log.info("Fetching all courses with pagination: {}", pageable);
        return courseRepo.findAll(pageable)
                .map(CourseResponse::fromCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        log.info("Fetching course with id: {}", id);
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return CourseResponse.fromCourse(course);
    }

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        log.info("Creating new course: {}", courseRequest.getTitle());
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setPrice(courseRequest.getPrice());
        course.setInstructor(courseRequest.getInstructor());
        course.setDurationHours(courseRequest.getDurationHours());
        course.setLevel(courseRequest.getLevel());
        course.setIsActive(courseRequest.getIsActive());
        
        Course savedCourse = courseRepo.save(course);
        log.info("Course created successfully with id: {}", savedCourse.getId());
        return CourseResponse.fromCourse(savedCourse);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        log.info("Updating course with id: {}", id);
        Course existingCourse = courseRepo.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        
        existingCourse.setTitle(courseRequest.getTitle());
        existingCourse.setDescription(courseRequest.getDescription());
        existingCourse.setPrice(courseRequest.getPrice());
        existingCourse.setInstructor(courseRequest.getInstructor());
        existingCourse.setDurationHours(courseRequest.getDurationHours());
        existingCourse.setLevel(courseRequest.getLevel());
        existingCourse.setIsActive(courseRequest.getIsActive());
        
        Course updatedCourse = courseRepo.save(existingCourse);
        log.info("Course updated successfully with id: {}", updatedCourse.getId());
        return CourseResponse.fromCourse(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);
        if (!courseRepo.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepo.deleteById(id);
        log.info("Course deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> searchCourses(String keyword) {
        log.info("Searching courses with keyword: {}", keyword);
        return courseRepo.searchCourses(keyword).stream()
                .map(CourseResponse::fromCourse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByInstructor(String instructor) {
        log.info("Fetching courses by instructor: {}", instructor);
        return courseRepo.findByInstructor(instructor).stream()
                .map(CourseResponse::fromCourse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByLevel(Course.CourseLevel level) {
        log.info("Fetching courses by level: {}", level);
        return courseRepo.findByLevel(level).stream()
                .map(CourseResponse::fromCourse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Fetching courses by price range: {} - {}", minPrice, maxPrice);
        return courseRepo.findByPriceBetween(minPrice, maxPrice).stream()
                .map(CourseResponse::fromCourse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getActiveCourses() {
        log.info("Fetching active courses");
        return courseRepo.findByIsActiveTrue().stream()
                .map(CourseResponse::fromCourse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseResponse> getActiveCourses(Pageable pageable) {
        log.info("Fetching active courses with pagination: {}", pageable);
        return courseRepo.findByIsActiveTrue(pageable)
                .map(CourseResponse::fromCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCourseCount() {
        log.info("Getting total course count");
        return courseRepo.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCourseCountByInstructor(String instructor) {
        log.info("Getting course count for instructor: {}", instructor);
        return courseRepo.countByInstructor(instructor);
    }
}




