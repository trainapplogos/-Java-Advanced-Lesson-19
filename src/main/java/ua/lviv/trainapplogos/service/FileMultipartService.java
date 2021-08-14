package ua.lviv.trainapplogos.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ua.lviv.trainapplogos.domain.FileMultipart;
import ua.lviv.trainapplogos.repository.FileMultipartRepository;

@Service
public class FileMultipartService {
	@Autowired
	private FileMultipartRepository fileMultipartRepository;
	
	public FileMultipart storeFile(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileMultipart multipart = null;
		
		if (!fileName.contains("..")) {
			multipart = new FileMultipart(fileName, file.getContentType(), file.getBytes());
		}
		
		return fileMultipartRepository.save(multipart);
	}
	
	public FileMultipart getFile(String fileId) throws FileNotFoundException {
		return fileMultipartRepository.findById(fileId).orElseThrow(() -> new FileNotFoundException("Coudn't find file with id = " + fileId));
	}
	
	
}
