package com.adel.coursecatalogservice.controller

import com.adel.coursecatalogservice.dto.CourseDTO
import com.adel.coursecatalogservice.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(
    val courseService: CourseService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO) = courseService.addCourse(courseDTO)


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCourses(@RequestParam("course_name", required = false) courseName: String?) = courseService.retrieveAllCourses(courseName)

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Int, @RequestBody courseDTO: CourseDTO) = courseService.updateCourse(courseId, courseDTO)

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable courseId: Int) = courseService.deleteCourse(courseId)
}