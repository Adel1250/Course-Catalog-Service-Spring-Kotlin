package com.adel.coursecatalogservice.controller

import com.adel.coursecatalogservice.dto.CourseDTO
import com.adel.coursecatalogservice.entity.CourseEntity
import com.adel.coursecatalogservice.repository.CourseRepository
import com.adel.coursecatalogservice.repository.InstructorRepository
import com.adel.coursecatalogservice.util.courseEntityList
import com.adel.coursecatalogservice.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val instructor = instructorEntity()
        instructorRepository.save(instructor)
        val courses = courseEntityList(instructor)
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val instructor = instructorRepository.findAll().first()
        val courseDTO = CourseDTO(null, "Build Restful APIs using Kotlin and SpringBoot", "Development", instructor.id)
        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {
        val courses = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(3, courses!!.size)
    }

    @Test
    fun retrieveAllCoursesByName() {
        val courses = webTestClient
            .get()
            .uri(
                UriComponentsBuilder.fromUriString("/v1/courses")
                    .queryParam("course_name", "SpringBoot")
                    .toUriString()
            )
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, courses!!.size)
    }

    @Test
    fun updateCourse() {
        val instructor = instructorRepository.findAll().first()
        val course = CourseEntity(null, "Build RestFul APis using SpringBoot and Kotlin", "Development", instructor)
        courseRepository.save(course)

        val courseDTO = CourseDTO(null, "Kotlin", "Development", instructor.id)
        courseRepository.save(course)

        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", course.id)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Kotlin", updatedCourseDTO!!.name)
    }

    @Test
    fun deleteCourse() {
        val course = courseRepository.findAll().first()
        webTestClient
            .delete()
            .uri("/v1/courses/${course.id}")
            .exchange()
            .expectStatus().is2xxSuccessful

        Assertions.assertEquals(2, courseRepository.count())
    }
}