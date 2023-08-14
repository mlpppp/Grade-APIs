package com.ltp.gradesubmission.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ltp.gradesubmission.service.CourseService;

import lombok.Setter;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Setter
public class GradeSubmissionApplicationTests {

        @Autowired
        private MockMvc mockmvc;

        @Test
        void contextLoads() {
                assertNotNull(mockmvc);
        }

        // ! Course
        @Test
        public void testGetCourse() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/course/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testSaveCourse() throws Exception {
                String jsonContent = "{    \"subject\": \"potions\",\r\n" + //
                                "    \"code\": \"POT-1123\",\r\n" + //
                                "    \"description\": \"In this class, students learn the correct way to brew potions.\"}";
                RequestBuilder request = MockMvcRequestBuilders.post("/api/course/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonContent);
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testDeleteCourse() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.delete("/api/course/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

        @Test
        public void testGetCourses() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/course/all");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testEnrollStudentToCourse() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.put("/api/course/1/student/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetEnrolledStudents() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/course/1/students");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        // ! Student
        @Test
        public void testGetStudent() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/student/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testSaveStudent() throws Exception {
                String jsonContent = "{ \"name\": \"Harry Potter\", \"birthDate\": \"1980-07-31\"}";
                RequestBuilder request = MockMvcRequestBuilders.post("/api/student/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonContent);
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testDeleteStudent() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.delete("/api/student/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

        @Test
        public void testGetStudents() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/student/all");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetEnrolledCourses() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/student/1/courses");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        // ! Grade
        @Test
        public void testSaveGrade() throws Exception {

                String jsonContent = "{ \"score\": \"A+\"}";
                RequestBuilder request = MockMvcRequestBuilders.post("/api/grade/student/2/course/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonContent);
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetGrade() throws Exception {

                RequestBuilder request = MockMvcRequestBuilders.get("/api/grade/student/1/course/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testUpdateGrade() throws Exception {
                String jsonContent = "{ \"score\": \"B+\"}";

                RequestBuilder request = MockMvcRequestBuilders.put("/api/grade/student/1/course/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonContent);
                ;
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testDeleteGrade() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.delete("/api/grade/student/1/course/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

        @Test
        public void testrGetStudentGrades() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/grade/student/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetCourseGrades() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/grade/course/1");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetGrades() throws Exception {
                RequestBuilder request = MockMvcRequestBuilders.get("/api/grade/all");
                mockmvc.perform(request) // it throws checked exception
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

}
