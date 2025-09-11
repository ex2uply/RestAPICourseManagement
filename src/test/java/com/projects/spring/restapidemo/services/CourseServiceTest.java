package com.projects.spring.restapidemo.services;

import com.projects.spring.restapidemo.dto.CourseRequest;
import com.projects.spring.restapidemo.exception.CourseNotFoundException;
import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.repo.CourseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private ServiceCourse courseService;

    private Course testCourse;
    private CourseRequest testCourseRequest;

    @BeforeEach
    void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setTitle("Java Programming");
        testCourse.setDescription("Learn Java programming fundamentals");
        testCourse.setPrice(new BigDecimal("99.99"));
        testCourse.setInstructor("John Doe");
        testCourse.setDurationHours(40);
        testCourse.setLevel(Course.CourseLevel.BEGINNER);
        testCourse.setIsActive(true);
        testCourse.setCreatedAt(LocalDateTime.now());
        testCourse.setUpdatedAt(LocalDateTime.now());

        testCourseRequest = new CourseRequest();
        testCourseRequest.setTitle("Java Programming");
        testCourseRequest.setDescription("Learn Java programming fundamentals");
        testCourseRequest.setPrice(new BigDecimal("99.99"));
        testCourseRequest.setInstructor("John Doe");
        testCourseRequest.setDurationHours(40);
        testCourseRequest.setLevel(Course.CourseLevel.BEGINNER);
        testCourseRequest.setIsActive(true);
    }

    @Test
    void testGetAllCourses() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.findAll()).thenReturn(courses);

        // When
        List<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService.getAllCourses();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Java Programming");
        verify(courseRepo).findAll();
    }

    @Test
    void testGetAllCoursesWithPagination() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> coursePage = new PageImpl<>(Arrays.asList(testCourse));
        when(courseRepo.findAll(pageable)).thenReturn(coursePage);

        // When
        Page<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService.getAllCourses(pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Java Programming");
        verify(courseRepo).findAll(pageable);
    }

    @Test
    void testGetCourseById() {
        // Given
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));

        // When
        com.projects.spring.restapidemo.dto.CourseResponse result = courseService.getCourseById(1L);

        // Then
        assertThat(result.getTitle()).isEqualTo("Java Programming");
        assertThat(result.getId()).isEqualTo(1L);
        verify(courseRepo).findById(1L);
    }

    @Test
    void testGetCourseByIdNotFound() {
        // Given
        when(courseRepo.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> courseService.getCourseById(1L))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("Course not found with id: 1");
        verify(courseRepo).findById(1L);
    }

    @Test
    void testCreateCourse() {
        // Given
        when(courseRepo.save(any(Course.class))).thenReturn(testCourse);

        // When
        com.projects.spring.restapidemo.dto.CourseResponse result = courseService.createCourse(testCourseRequest);

        // Then
        assertThat(result.getTitle()).isEqualTo("Java Programming");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("99.99"));
        verify(courseRepo).save(any(Course.class));
    }

    @Test
    void testUpdateCourse() {
        // Given
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));
        when(courseRepo.save(any(Course.class))).thenReturn(testCourse);

        // When
        com.projects.spring.restapidemo.dto.CourseResponse result = courseService.updateCourse(1L, testCourseRequest);

        // Then
        assertThat(result.getTitle()).isEqualTo("Java Programming");
        verify(courseRepo).findById(1L);
        verify(courseRepo).save(any(Course.class));
    }

    @Test
    void testUpdateCourseNotFound() {
        // Given
        when(courseRepo.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> courseService.updateCourse(1L, testCourseRequest))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("Course not found with id: 1");
        verify(courseRepo).findById(1L);
        verify(courseRepo, never()).save(any(Course.class));
    }

    @Test
    void testDeleteCourse() {
        // Given
        when(courseRepo.existsById(1L)).thenReturn(true);

        // When
        courseService.deleteCourse(1L);

        // Then
        verify(courseRepo).existsById(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    void testDeleteCourseNotFound() {
        // Given
        when(courseRepo.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> courseService.deleteCourse(1L))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("Course not found with id: 1");
        verify(courseRepo).existsById(1L);
        verify(courseRepo, never()).deleteById(anyLong());
    }

    @Test
    void testSearchCourses() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.searchCourses("java")).thenReturn(courses);

        // When
        List<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService.searchCourses("java");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Java Programming");
        verify(courseRepo).searchCourses("java");
    }

    @Test
    void testGetCoursesByInstructor() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.findByInstructor("John Doe")).thenReturn(courses);

        // When
        List<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService
                .getCoursesByInstructor("John Doe");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getInstructor()).isEqualTo("John Doe");
        verify(courseRepo).findByInstructor("John Doe");
    }

    @Test
    void testGetCoursesByLevel() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.findByLevel(Course.CourseLevel.BEGINNER)).thenReturn(courses);

        // When
        List<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService
                .getCoursesByLevel(Course.CourseLevel.BEGINNER);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLevel()).isEqualTo(Course.CourseLevel.BEGINNER);
        verify(courseRepo).findByLevel(Course.CourseLevel.BEGINNER);
    }

    @Test
    void testGetCoursesByPriceRange() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.findByPriceBetween(new BigDecimal("50"), new BigDecimal("150"))).thenReturn(courses);

        // When
        List<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService.getCoursesByPriceRange(
                new BigDecimal("50"), new BigDecimal("150"));

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPrice()).isEqualTo(new BigDecimal("99.99"));
        verify(courseRepo).findByPriceBetween(new BigDecimal("50"), new BigDecimal("150"));
    }

    @Test
    void testGetActiveCourses() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepo.findByIsActiveTrue()).thenReturn(courses);

        // When
        List<com.projects.spring.restapidemo.dto.CourseResponse> result = courseService.getActiveCourses();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIsActive()).isTrue();
        verify(courseRepo).findByIsActiveTrue();
    }

    @Test
    void testGetCourseCount() {
        // Given
        when(courseRepo.count()).thenReturn(5L);

        // When
        long result = courseService.getCourseCount();

        // Then
        assertThat(result).isEqualTo(5L);
        verify(courseRepo).count();
    }

    @Test
    void testGetCourseCountByInstructor() {
        // Given
        when(courseRepo.countByInstructor("John Doe")).thenReturn(3L);

        // When
        long result = courseService.getCourseCountByInstructor("John Doe");

        // Then
        assertThat(result).isEqualTo(3L);
        verify(courseRepo).countByInstructor("John Doe");
    }
}
