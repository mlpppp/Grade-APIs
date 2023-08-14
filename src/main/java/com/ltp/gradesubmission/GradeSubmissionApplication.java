package com.ltp.gradesubmission;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.CourseService;
import com.ltp.gradesubmission.service.GradeService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class GradeSubmissionApplication implements CommandLineRunner {

	private StudentRepository studentRepository;
	private CourseRepository courseRepository;
	private CourseService courseService;
	private GradeService gradeService;

	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Student[] students = new Student[] {
				new Student("Harry Potter", LocalDate.parse(("1980-07-31"))),
				new Student("Ron Weasley", LocalDate.parse(("1980-03-01"))),
				new Student("Hermione Granger", LocalDate.parse(("1979-09-19"))),
				new Student("Neville Longbottom", LocalDate.parse(("1980-07-30")))
		};

		for (int i = 0; i < students.length; i++) {
			studentRepository.save(students[i]);
		}

		Course[] courses = new Course[] {
				new Course("Charms", "CH104",
						"In this class, you will learn spells concerned with giving an object new and unexpected properties."),
				new Course("Defence Against the Dark Arts", "DADA",
						"In this class, you will learn defensive techniques against the dark arts."),
				new Course("Herbology", "HB311",
						"In this class, you will learn the study of magical plants and how to take care of, utilise and combat them."),
				new Course("History of Magic", "HIS393",
						"In this class, you will learn about significant events in wizarding history."),
				new Course("Potions", "POT102",
						"In this class, you will learn correct mixing and stirring of ingredients to create mixtures with magical effects."),
				new Course("Transfiguration", "TR442",
						"In this class, you will learn the art of changing the form or appearance of an object.")
		};

		for (int i = 0; i < courses.length; i++) {
			courseRepository.save(courses[i]);
		}

		// Course #1: 1, 2; Student #1: 1, 2
		courseService.addStudentToCourse(1L, 1L);
		courseService.addStudentToCourse(2L, 1L);
		courseService.addStudentToCourse(1L, 2L);
		courseService.addStudentToCourse(2L, 2L);

		Grade grade1 = new Grade();
		Grade grade2 = new Grade();
		Grade grade3 = new Grade();
		grade1.setScore("A+");
		grade2.setScore("B+");
		grade3.setScore("C+");

		gradeService.saveGrade(grade1, 1L, 1L);
		gradeService.saveGrade(grade2, 1L, 2L);
		gradeService.saveGrade(grade3, 2L, 1L);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
