package com.gdky.restfull.api;

import java.io.File;
import java.io.IOException;





import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gdky.restfull.configuration.Constants;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
public class FileUploadController {
	
	@RequestMapping(value="/upload", method= RequestMethod.GET)
    public String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(name);
            File to = new File(Constants.UPLOAD_LOCATION+Hashing.crc32().hashBytes(file.getBytes())+"."+ext);
            try {
                Files.write(file.getBytes(), to);
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }
}
