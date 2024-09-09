package com.poc.FileSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.FileSystem.service.FileStorageService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/fileService")
public class FileSystemController {

	
	@Autowired
	FileStorageService service;
	
	private String sourceFilePath = "D:\\FileRepository\\SampleFiles\\";
	private String destinationFilePath = "D:\\FileRepository\\Destination\\";
	
	
	
	@RequestMapping("/message")
	public String getMessage() {
		return " Hello Surya - Spring app is Up and Running ";
	}
	
	
	@RequestMapping("/uploadLargeFile/{fileName}")
	public String uploadLargeFile(@PathVariable String fileName) {
		
		
		
		service.uploadfile(sourceFilePath, destinationFilePath, fileName);
		
		
		return " File Upload Successful ";
		
		
	}
	
	
	
	
}
