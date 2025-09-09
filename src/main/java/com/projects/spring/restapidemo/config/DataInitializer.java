package com.projects.spring.restapidemo.config;

import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CourseRepo courseRepo;

    @Override
    public void run(String... args) throws Exception {
        if (courseRepo.count() == 0) {
            log.info("Initializing sample data...");
            
            // Sample courses
            Course course1 = new Course();
            course1.setTitle("Java Programming Fundamentals");
            course1.setDescription("Learn the basics of Java programming including variables, loops, and object-oriented concepts");
            course1.setPrice(new BigDecimal("99.99"));
            course1.setInstructor("John Smith");
            course1.setDurationHours(40);
            course1.setLevel(Course.CourseLevel.BEGINNER);
            course1.setIsActive(true);
            
            Course course2 = new Course();
            course2.setTitle("Advanced Spring Boot Development");
            course2.setDescription("Master Spring Boot framework for building enterprise-grade applications");
            course2.setPrice(new BigDecimal("199.99"));
            course2.setInstructor("Jane Doe");
            course2.setDurationHours(60);
            course2.setLevel(Course.CourseLevel.ADVANCED);
            course2.setIsActive(true);
            
            Course course3 = new Course();
            course3.setTitle("React.js Complete Guide");
            course3.setDescription("Build modern web applications with React.js and hooks");
            course3.setPrice(new BigDecimal("149.99"));
            course3.setInstructor("Mike Johnson");
            course3.setDurationHours(50);
            course3.setLevel(Course.CourseLevel.INTERMEDIATE);
            course3.setIsActive(true);
            
            Course course4 = new Course();
            course4.setTitle("Database Design and SQL");
            course4.setDescription("Learn database design principles and SQL query optimization");
            course4.setPrice(new BigDecimal("79.99"));
            course4.setInstructor("Sarah Wilson");
            course4.setDurationHours(30);
            course4.setLevel(Course.CourseLevel.BEGINNER);
            course4.setIsActive(true);
            
            Course course5 = new Course();
            course5.setTitle("Microservices Architecture");
            course5.setDescription("Design and implement microservices using Spring Cloud and Docker");
            course5.setPrice(new BigDecimal("299.99"));
            course5.setInstructor("Jane Doe");
            course5.setDurationHours(80);
            course5.setLevel(Course.CourseLevel.ADVANCED);
            course5.setIsActive(false);
            
            courseRepo.save(course1);
            courseRepo.save(course2);
            courseRepo.save(course3);
            courseRepo.save(course4);
            courseRepo.save(course5);
            
            log.info("Sample data initialized successfully. Created {} courses.", courseRepo.count());
        } else {
            log.info("Database already contains {} courses. Skipping data initialization.", courseRepo.count());
        }
    }
}
