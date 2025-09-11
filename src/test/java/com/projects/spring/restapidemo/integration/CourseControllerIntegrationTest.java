package com.projects.spring.restapidemo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.spring.restapidemo.dto.CourseRequest;
import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.repo.CourseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class CourseControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // Clear database before each test
        courseRepo.deleteAll();
    }

    @Test
    void testGetAllCourses() throws Exception {
        // Create test data
        createTestCourse("Java Basics", "Learn Java", new BigDecimal("99.99"));

        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content.length()").value(1))
                .andExpect(jsonPath("$.data.content[0].title").value("Java Basics"));
    }

    @Test
    void testCreateCourse() throws Exception {
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setTitle("Spring Boot Advanced");
        courseRequest.setDescription("Learn advanced Spring Boot concepts");
        courseRequest.setPrice(new BigDecimal("199.99"));
        courseRequest.setInstructor("Jane Doe");
        courseRequest.setDurationHours(60);
        courseRequest.setLevel(Course.CourseLevel.ADVANCED);
        courseRequest.setIsActive(true);

        mockMvc.perform(post("/api/v1/courses")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Spring Boot Advanced"));
    }

    @Test
    void testCreateCourseWithoutAuthentication() throws Exception {
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setTitle("Unauthorized Course");
        courseRequest.setDescription("This should fail");
        courseRequest.setPrice(new BigDecimal("99.99"));
        courseRequest.setInstructor("Test Instructor");
        courseRequest.setDurationHours(30);
        courseRequest.setLevel(Course.CourseLevel.BEGINNER);
        courseRequest.setIsActive(true);

        mockMvc.perform(post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testSearchCourses() throws Exception {
        // Create test data
        createTestCourse("Java Programming", "Learn Java programming", new BigDecimal("99.99"));
        createTestCourse("Python Basics", "Learn Python programming", new BigDecimal("89.99"));

        mockMvc.perform(get("/api/v1/courses/search")
                .param("keyword", "java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Java Programming"));
    }

    @Test
    void testGetCoursesByPriceRange() throws Exception {
        // Create test data
        createTestCourse("Cheap Course", "Affordable course", new BigDecimal("50.00"));
        createTestCourse("Expensive Course", "Premium course", new BigDecimal("300.00"));

        mockMvc.perform(get("/api/v1/courses/price-range")
                .param("minPrice", "40")
                .param("maxPrice", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Cheap Course"));
    }

    @Test
    void testValidationErrors() throws Exception {
        CourseRequest invalidRequest = new CourseRequest();
        // Missing required fields

        mockMvc.perform(post("/api/v1/courses")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isMap());
    }

    private void createTestCourse(String title, String description, BigDecimal price) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setInstructor("Test Instructor");
        course.setDurationHours(40);
        course.setLevel(Course.CourseLevel.BEGINNER);
        course.setIsActive(true);
        courseRepo.save(course);
    }
}
