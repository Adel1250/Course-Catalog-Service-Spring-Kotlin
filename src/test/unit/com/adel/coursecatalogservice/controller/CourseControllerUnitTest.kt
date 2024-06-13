package com.adel.coursecatalogservice.controller

import com.adel.coursecatalogservice.dto.CourseDTO
import com.adel.coursecatalogservice.service.CourseService
import com.adel.coursecatalogservice.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(null, "Build Restful APIs using Kotlin and SpringBoot", "Development")

        every {
            courseServiceMock.addCourse(any())
        } returns courseDTO(id = 1)

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
    fun addCourseValidation() {
        val courseDTO = CourseDTO(null, "", "")

        every {
            courseServiceMock.addCourse(any())
        } returns courseDTO(id = 1)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("Course Category must not be blank, Course Name must not be blank", response)
    }

    @Test
    fun addCourseRuntimeException() {
        val courseDTO = CourseDTO(null, "Build Restful APIs using Kotlin and SpringBoot", "Development")
        val errorMessage = "Unexpected error happens"

        every {
            courseServiceMock.addCourse(any())
        } throws RuntimeException(errorMessage)

        webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    fun retrieveAllCourses() {
        every {
            courseServiceMock.retrieveAllCourses(null)
        }.returnsMany(
            listOf(courseDTO(1))
        )

        val courses = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(1, courses!!.size)
    }

    @Test
    fun updateCourse() {
        every {
            courseServiceMock.updateCourse(any(), any())
        } returns (courseDTO(id = 100, name = "Kotlin"))

        val updatesCourseDTO = CourseDTO(100, "Kotlin", "Development")

        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(updatesCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Kotlin", updatedCourseDTO!!.name)
    }

    @Test
    fun deleteCourse() {
        every {
            courseServiceMock.deleteCourse(any())
        } just runs

        webTestClient
            .delete()
            .uri("/v1/courses/1")
            .exchange()
            .expectStatus().isNoContent
    }
}