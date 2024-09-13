package com.projects.spring.restapidemo.services;

import com.projects.spring.restapidemo.model.Course;

import java.util.List;

public interface CourseService {

    public List<Course> getALLCourses();
    public Course getCourse(long Id);

    void addCourse(Course course);

    void deleteCourse(long l);

    void updateCourse(Course course);
}
