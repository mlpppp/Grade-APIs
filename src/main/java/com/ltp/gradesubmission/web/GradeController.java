package com.ltp.gradesubmission.web;

import java.util.List;

import javax.validation.Valid;

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

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.GradeService;

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
@RequestMapping("/api/grade")
@Tag(name = "Grade Controller", description = "Create, Delete, Update and Retrieve Grades")
public class GradeController {

    GradeService gradeService;

    @GetMapping(value = "/student/{studentId}/course/{courseId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves Grade", description = "Retrieve a single grade record with studentId and courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "The requested grade with does not exist.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Grade> getGrade(@PathVariable Long studentId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getGrade(studentId, courseId), HttpStatus.OK);
    }

    @PostMapping(value = "/student/{studentId}/course/{courseId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Grade", description = "Create a grade record with studentId and courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success Created", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "400", description = "The score is not valid", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "The student is not enrolled in the course", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Grade> saveGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.saveGrade(grade, studentId, courseId), HttpStatus.CREATED);
    }

    @PutMapping(value = "/student/{studentId}/course/{courseId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Grade", description = "Update a grade record with studentId and courseId")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updated", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "400", description = "The score is not valid", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
            @ApiResponse(responseCode = "404", description = "The student is not enrolled in the course", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.updateGrade(grade.getScore(), studentId, courseId), HttpStatus.OK);
    }

    @DeleteMapping("/student/{studentId}/course/{courseId}")
    @Operation(summary = "Delete Grade", description = "Update a grade record with studentId and courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The record is deleted"),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable Long studentId, @PathVariable Long courseId) {
        gradeService.deleteGrade(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/student/{studentId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve Student's Grades", description = "Retrieve a List of Grade of a studentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Grade.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long studentId) {
        return new ResponseEntity<>(gradeService.getStudentGrades(studentId), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve Grades of a Course", description = "Retrieve a List of Grade of a Course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Grade.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<List<Grade>> getCourseGrades(@PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getCourseGrades(courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve All Grades", description = "Retrieve a List of all Grades in the database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Grade.class)))),
            @ApiResponse(responseCode = "403", description = "User doesn't Authenticated", content = @Content(schema = @Schema(defaultValue = "403 Forbidden"))),
    })
    public ResponseEntity<List<Grade>> getGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }

}
