package com.superindo.cashier.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.superindo.cashier.response.MessageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

	@PostMapping("image")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile file) {
		if (!file.getContentType().startsWith("image")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse("Invalid file type. Only images are allowed."));
		}

		try {
			byte[] bytes = file.getBytes();
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			Path path = Paths.get("src/main/resources/static/images/" + fileName);

			Files.write(path, bytes);

			String relativePath = "images/" + fileName;
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(relativePath));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error uploading image"));
		}
	}
}
