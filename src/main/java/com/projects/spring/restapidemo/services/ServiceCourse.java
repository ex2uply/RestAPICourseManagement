package com.projects.spring.restapidemo.services;

import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCourse implements CourseService{
    final
    CourseRepo repo;

    public ServiceCourse(CourseRepo repo) {
        this.repo = repo;
    }


    @Override
    public List<Course> getALLCourses() {
        return repo.findAll();
    }

    @Override
    public Course getCourse(long id){
        return repo.getReferenceById(id);
    }

    @Override
    public void addCourse(Course course) {
        repo.save(course);
    }

    @Override
    public void deleteCourse(long l) {
        repo.deleteById(l);
    }

    @Override
    public void updateCourse(Course course) {
        repo.save(course);
    }
}




