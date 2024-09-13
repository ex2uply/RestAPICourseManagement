package com.projects.spring.restapidemo.controller;


import com.projects.spring.restapidemo.model.Course;
import com.projects.spring.restapidemo.services.CourseService;
import com.projects.spring.restapidemo.services.ServiceCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class Controller {

    final CourseService service;
    private final ResourceUrlProvider mvcResourceUrlProvider;

    public Controller(CourseService service, ResourceUrlProvider mvcResourceUrlProvider) {
        this.service = service;
        this.mvcResourceUrlProvider = mvcResourceUrlProvider;
    }

    @GetMapping("/home")
    public String home(){
        return "this is home page";
    }


    //get courses
    @GetMapping("/courses")
    public List<Course> getCourses(){
        return service.getALLCourses();
    }

    //get particular courses

    @GetMapping("/courses/{courseID}")
    public Course getCourse(@PathVariable String courseID){
        return service.getCourse(Long.parseLong(courseID));

    }
    @PostMapping("/courses")
    public void  addCourse(@RequestBody Course course){
            service.addCourse(course);
    }

    @DeleteMapping("/courses/{courseID}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable String courseID){
        try{
            service.deleteCourse(Long.parseLong(courseID));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/courses")
    public ResponseEntity<HttpStatus> updateCourse(@RequestBody Course course){
        try{
            service.updateCourse(course);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
