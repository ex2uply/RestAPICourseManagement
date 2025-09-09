package com.projects.spring.restapidemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.spring.restapidemo.dto.CourseRequest;
import com.projects.spring.restapidemo.dto.CourseResponse;
import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.services.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/courses/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("API is healthy"));
    }

    @Test
    void testGetAllCourses() throws Exception {
        CourseResponse course1 = createSampleCourseResponse(1L, "Java Basics", "Learn Java", new BigDecimal("99.99"));
        CourseResponse course2 = createSampleCourseResponse(2L, "Spring Boot", "Learn Spring", new BigDecimal("199.99"));
        
        List<CourseResponse> courses = Arrays.asList(course1, course2);
        Page<CourseResponse> coursePage = new PageImpl<>(courses, PageRequest.of(0, 10), 2);
        
        when(courseService.getAllCourses(any())).thenReturn(coursePage);

        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content.length()").value(2));
    }

    @Test
    void testGetCourseById() throws Exception {
        CourseResponse course = createSampleCourseResponse(1L, "Java Basics", "Learn Java", new BigDecimal("99.99"));
        
        when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/api/v1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Java Basics"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCourse() throws Exception {
        CourseRequest courseRequest = createSampleCourseRequest("Java Basics", "Learn Java", new BigDecimal("99.99"));
        CourseResponse courseResponse = createSampleCourseResponse(1L, "Java Basics", "Learn Java", new BigDecimal("99.99"));
        
        when(courseService.createCourse(any(CourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(post("/api/v1/courses")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Java Basics"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateCourse() throws Exception {
        CourseRequest courseRequest = createSampleCourseRequest("Updated Java", "Updated Description", new BigDecimal("149.99"));
        CourseResponse courseResponse = createSampleCourseResponse(1L, "Updated Java", "Updated Description", new BigDecimal("149.99"));
        
        when(courseService.updateCourse(eq(1L), any(CourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(put("/api/v1/courses/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Updated Java"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Course deleted successfully"));
    }

    @Test
    void testSearchCourses() throws Exception {
        CourseResponse course = createSampleCourseResponse(1L, "Java Basics", "Learn Java", new BigDecimal("99.99"));
        List<CourseResponse> courses = Arrays.asList(course);
        
        when(courseService.searchCourses("java")).thenReturn(courses);

        mockMvc.perform(get("/api/v1/courses/search")
                        .param("keyword", "java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    private CourseRequest createSampleCourseRequest(String title, String description, BigDecimal price) {
        CourseRequest request = new CourseRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setPrice(price);
        request.setInstructor("John Doe");
        request.setDurationHours(40);
        request.setLevel(Course.CourseLevel.BEGINNER);
        request.setIsActive(true);
        return request;
    }

    private CourseResponse createSampleCourseResponse(Long id, String title, String description, BigDecimal price) {
        CourseResponse response = new CourseResponse();
        response.setId(id);
        response.setTitle(title);
        response.setDescription(description);
        response.setPrice(price);
        response.setInstructor("John Doe");
        response.setDurationHours(40);
        response.setLevel(Course.CourseLevel.BEGINNER);
        response.setIsActive(true);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());
        return response;
    }
}
