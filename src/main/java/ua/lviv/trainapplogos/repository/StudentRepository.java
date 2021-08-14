package ua.lviv.trainapplogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.lviv.trainapplogos.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
