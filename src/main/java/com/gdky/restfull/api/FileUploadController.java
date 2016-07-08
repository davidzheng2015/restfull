package com.gdky.restfull.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gdky.restfull.configuration.Constants;
import com.google.common.io.Files;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
@MultipartConfig(
		  location = "/tmp/", 
		  maxFileSize = 1024L * 1024L, // 每一个文件的最大值  1MB
		  maxRequestSize = 1024L * 1024L * 10L // 一次上传最大值，若每次只能上传一个文件，则设置maxRequestSize意义不大  10MB
		)
public class FileUploadController {
	
	@RequestMapping(value="/upload", method= RequestMethod.GET)
    public String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            File to = new File(name);
            try {
                Files.write(file.getBytes(), new File(name));
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }
}
