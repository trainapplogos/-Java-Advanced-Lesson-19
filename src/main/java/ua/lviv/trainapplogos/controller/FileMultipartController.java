package ua.lviv.trainapplogos.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ua.lviv.trainapplogos.domain.FileMultipart;
import ua.lviv.trainapplogos.dto.MultipartUploadResponse;
import ua.lviv.trainapplogos.service.FileMultipartService;

@RestController
public class FileMultipartController {

	@Autowired
	private FileMultipartService fileMultipartService;

	@PostMapping("/uploadFileDB")
	public MultipartUploadResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		FileMultipart fileMultipart = fileMultipartService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFileDB/")
				.path(fileMultipart.getId()).toUriString();
		
		return new MultipartUploadResponse(fileMultipart.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@GetMapping("/downloadFileDB/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws FileNotFoundException {
		FileMultipart fileMultipart = fileMultipartService.getFile(fileId);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fileMultipart.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + fileMultipart.getFileName() + "\"")
				.body(new ByteArrayResource(fileMultipart.getData()));
	}
	
//	@RequestMapping(path = {"/save"}, method = RequestMethod.POST)
	//@PostMapping("/save")
/*	public String save(@ModelAttribute Participant participant, HttpServletRequest req) {
		participantService.save(participant);
		req.setAttribute("participants", participantService.findAllParticipants());
		req.setAttribute("mode", "PARTICIPANT_VIEW");
		return "index";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam Integer id, HttpServletRequest req) {
			req.setAttribute("participant", participantService.findOne(id));
			req.setAttribute("mode", "PARTICIPANT_EDIT");
			return "index";
	} */	
}