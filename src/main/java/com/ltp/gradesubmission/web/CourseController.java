package com.ltp.gradesubmission.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/course")
@Tag(name = "Course Controller", description = "Create, Delete, Update and Retrieve Course")
public class CourseController {

    CourseService courseService;

    @GetMapping(value = "/{courseId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves Course Info", description = "Retrieve a single Course record with courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(schema = @Schema(implementation = Course.class)))
    })
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Course", description = "Create a Course record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden")))
    })
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Delete Course", description = "Delete a Course record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "204", description = "Successful Deletion"),
            @ApiResponse(responseCode = "404", description = "Cannot delete non-existing resource", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve All Courses", description = "Retrieve a List of all Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<List<Course>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @PutMapping(value = "/{courseId}/student/{studentId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Enroll Student To Course", description = "Add a student with studentId to the registerd student List in a course with courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully enrolled student", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "The course or student with the id does not exist.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Course> enrollStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        return new ResponseEntity<>(courseService.addStudentToCourse(studentId, courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}/students", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Enrolled Students", description = "Retrieve a list of all student records that have registered to course with courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "The course with the id does not exist.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Set<Student>> getEnrolledStudents(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getEnrolledStudents(courseId), HttpStatus.OK);
    }

}