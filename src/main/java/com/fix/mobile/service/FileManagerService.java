package com.fix.mobile.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManagerService {
	@Autowired
	ServletContext app;
	private Path getPath(String folder, String filename) {
		File dir = Paths.get(app.getRealPath("/files/"),folder).toFile();
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return Paths.get(dir.getAbsolutePath(),filename);
	}
	public byte[] read(String folder,String filename) {
		Path path = this.getPath(folder, filename);
		try {
			return Files.readAllBytes(path);
		}catch(IOException e) {
			throw new RuntimeException();
		}
	}
	public File save(MultipartFile file, String folder) {
		File dir = new File(System.getProperty("user.dir")+"/src/main/resources/static/images/"+folder);
		System.out.println("- RealPath: "+System.getProperty("user.dir"));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		try {
			String filename = file.getOriginalFilename();
			System.out.println("filename: "+filename);
			System.out.println("save run b2");
			File savedFile = new File(dir,filename);
			if(!savedFile.exists()) {
				file.transferTo(savedFile);
			}
			System.out.println(savedFile.getAbsolutePath());
			System.out.println("save run b3");
			return savedFile;
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	public void delete(String folder,String filename) {
		Path path = this.getPath(folder, filename);
		path.toFile().delete();
	}
	public List<String> list(String folder){
		List<String> filenames = new ArrayList<String>();
		File dir = Paths.get(app.getRealPath("/files/"),folder).toFile();
		if(dir.exists()) {
			File[] files = dir.listFiles();
			for(File file:files) {
				filenames.add(file.getName());
			}
		}
		return filenames;
	}
}
