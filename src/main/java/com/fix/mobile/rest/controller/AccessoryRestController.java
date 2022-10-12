package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/admin/accessory")
public class AccessoryRestController {
	Logger LOGGER = Logger.getLogger(AccessoryRestController.class);
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
	//findall accessory pageable
	@GetMapping(value="/page/{page}")
	public List<Accessory> findAllPageable(@PathVariable("page") Optional<Integer> page){
		Pageable pageable = PageRequest.of(page.get(), 10);
		List<Accessory> accessories = accessoryService.findAll(pageable).getContent();
		LOGGER.info("findAllPageable: "+accessories);
		LOGGER.info("page: "+page);
		return accessories;
	}
	@GetMapping("/cate")
	public List<Category> findByCate(){
		return categoryService.findByType();
	}
	@PostMapping
	public Accessory save(@RequestBody Accessory accessory){
		LOGGER.info("save: "+accessory);
		return accessoryService.save(accessory);
	}
	//delete accessory
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id){
		accessoryService.deleteById(id);
		LOGGER.info("delete: "+id);
	}
	//update accessory
	@PutMapping("/{id}")
	public Accessory update(@PathVariable("id") Integer id, @RequestBody Accessory accessory){
		LOGGER.info("update: "+accessory);
		return accessoryService.update(accessory,id);
	}
}
