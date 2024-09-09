package com.poc.FileSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileSystem")
public class FileSystemController {

	@RequestMapping("/message")
	public String getMessage() {
		return "Hello Surya ";
	}
	
}
