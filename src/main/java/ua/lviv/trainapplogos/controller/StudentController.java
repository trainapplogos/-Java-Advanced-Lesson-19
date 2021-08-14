package ua.lviv.trainapplogos.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.lviv.trainapplogos.domain.Student;
import ua.lviv.trainapplogos.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/saveStudent")
	public String saveStudent(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, 
			@RequestParam("age") Integer age,  @RequestParam("imagePath") String imagePath, HttpServletRequest req) {
		Student savedStudent = studentService.save(firstName, lastName, age, imagePath);
		return "profile";
	}
	
	@GetMapping("/profile")
	public String init(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("age") Integer age, @RequestParam("imagePath") String imagePath, HttpServletRequest req,  HttpServletResponse res) throws ServletException, IOException {
		Student stud = new Student(firstName, lastName, age, imagePath);
		req.setAttribute("student", stud);
		req.getRequestDispatcher("profile.jsp").forward(req, res);
		return "profile";
	}
	
}
