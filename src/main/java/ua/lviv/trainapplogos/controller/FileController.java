package ua.lviv.trainapplogos.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ua.lviv.trainapplogos.domain.FileInfo;
import ua.lviv.trainapplogos.dto.MessageResponse;
import ua.lviv.trainapplogos.service.FileStorageService;

@Controller
@CrossOrigin("http://localhost:8081") //8080? configuring allowed origins
public class FileController {
	
	@Autowired
	FileStorageService fileStorageService;
	
	@PostConstruct
	public void init() {
		fileStorageService.deleteAll();
		fileStorageService.init();
	}
	
	@PreDestroy
	public void clearFilesStorage() {
		fileStorageService.deleteAll();
	}
	
	@PostMapping("/uploadFile")
	public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		
		try {
			fileStorageService.save(file);
			
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(fileStorageService.getClearFileName(file.getOriginalFilename())).toUriString();
			
			message = "The file was uploaded successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message, fileDownloadUri));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message, "---"));
		}	
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<FileInfo>> getListFiles() {
		List<FileInfo> fileInfos = fileStorageService.loadAll().map(path -> {
			String fileName = path.getFileName().toString();
			String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
			
			return new FileInfo(fileName, url);
		}).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = fileStorageService.load(filename);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
//	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
//	public ModelAndView method() {
//	    return new ModelAndView("redirect:" + "profile");
//	}
	
	
}
