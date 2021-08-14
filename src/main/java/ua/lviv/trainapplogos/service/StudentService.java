package ua.lviv.trainapplogos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.lviv.trainapplogos.domain.Student;
import ua.lviv.trainapplogos.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public Student save(String firstName, String lastName, int age, String imagePath) {
		return studentRepository.save(new Student(firstName, lastName, age, imagePath));
	}
}
