package com.ltp.gradesubmission.web;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Manage Users Authentication")
public class UserController {

	UserService userService;

	@PostMapping(value = "/authenticate", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "authenticate user", description = "Spring Security Authentication Endpoint, User has already authenticated.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = HttpServletResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "401", description = "You provided an incorrect password."),
			@ApiResponse(responseCode = "404", description = "Username doesn't exist")
	})
	public ResponseEntity<HttpStatus> login(@Valid @RequestBody User user) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	@Operation(summary = "Sign Up User", description = "Create a user record with a username and a password")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
	})
	public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
		userService.saveUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}