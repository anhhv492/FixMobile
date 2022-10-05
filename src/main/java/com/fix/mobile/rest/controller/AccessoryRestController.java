package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/admin/accessory")
public class AccessoryRestController {
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	ServletContext application;
	//findAll accessory
	@GetMapping
	public List<Accessory> findAll(){
		return accessoryService.findAll();
	}

	@GetMapping("/cate")
	public List<Category> findByCate(){
		return categoryService.findByType();
	}
	@PostMapping
	public Accessory save(@RequestBody Accessory accessory){
		return accessoryService.save(accessory);
	}
	//delete accessory
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id){
		accessoryService.deleteById(id);
	}
	//update accessory
	@PutMapping("/{id}")
	public Accessory update(@PathVariable("id") Integer id, @RequestBody Accessory accessory){
		return accessoryService.update(accessory,id);
	}
	@PostMapping("/save-file")
	public void saveFile(@RequestBody Accessory accessory, MultipartFile imageFile){
		if(!imageFile.isEmpty()){
			String path = application.getRealPath("/");
			System.out.println("path: "+path);
			try {
				accessory.setImage(imageFile.getOriginalFilename());
				System.out.println("image: "+accessory.getImage());
				String filePath = path+"/images/"+accessory.getImage();
				imageFile.transferTo(new File(filePath));
				imageFile=null;
				accessory.setImage(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
