package com.ltp.gradesubmission.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/student")
@Tag(name = "Student Controller", description = "Create, Delete, Update and Retrieve Student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/{studentId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves Student Info", description = "Retrieve a single Student record with courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getStudent(studentId), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Student", description = "Create a Student record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden")))
    })
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete Student", description = "Delete a Student record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Deletion"),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "Cannot delete non-existing resource", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve All Students", description = "Retrieve a List of all Students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping(value = "/{studentId}/courses", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Enrolled Courses", description = "Retrieve a list of all Course records that the student with studentId has registered to")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<Set<Course>> getEnrolledCourses(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getEnrolledCourses(studentId), HttpStatus.OK);
    }

}
