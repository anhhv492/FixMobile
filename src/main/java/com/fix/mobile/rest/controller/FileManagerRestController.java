package com.fix.mobile.rest.controller;

import java.io.File;
import java.util.List;

import javax.websocket.server.PathParam;

import com.fix.mobile.helper.ExcelHelper;
import com.fix.mobile.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/rest/files/images")
public class FileManagerRestController {
	@Autowired
	private ExcelHelper excelHelper;
	@Autowired
	FileManagerService fileService;
	@GetMapping("{folder}/{file}")
	public byte[] download(@PathVariable("folder") String folder,@PathVariable("file") String file) {
		return fileService.read(folder,file);
	}
	@PostMapping("/read-excel")
	public Boolean readExcel(@PathVariable("file") MultipartFile file) throws Exception{
		System.out.println("readExcel");
		try{
			Boolean checkExcel = excelHelper.readExcel(file);
			return checkExcel;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@PostMapping("/{folder}")
	public JsonNode upload(@PathVariable("folder") String folder,@PathParam("file") MultipartFile file) {
		System.out.println("manager file 1");
		File saveFile = fileService.save(file, folder);
		System.out.println("manager file 2");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", saveFile.getName());
		node.put("size", saveFile.length());
		return node;
	}
	@DeleteMapping("{folder}/{file}")
	public void delete(@PathVariable("folder") String folder,@PathVariable("file") String file) {
		fileService.delete(folder,file);
	}
	@GetMapping("{folder}")
	public List<String> list(@PathVariable("folder") String folder){
		return fileService.list(folder);
	}
}
