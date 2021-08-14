package ua.lviv.trainapplogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.trainapplogos.domain.FileMultipart;

@Repository
public interface FileMultipartRepository extends JpaRepository<FileMultipart, String> { 
	
	
}
