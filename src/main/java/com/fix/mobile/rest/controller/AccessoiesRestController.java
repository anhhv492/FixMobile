package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.*;
import com.fix.mobile.repository.RamRepository;
import com.fix.mobile.service.*;
import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/admin/accessoríes")
public class AccessoiesRestController {

	@Autowired
	private RamService ramService;
	@Autowired
	private ColorService colorService;

	@Autowired
	private CapacityService capacityService;

	@Autowired
	private ImageService  imageService;

	@Autowired
	ServletContext application;

	@Autowired
	private RamRepository ramReponsitory;


	@GetMapping
	public List<Ram> findall(){
		return ramService.findAll();
	}


	@PostMapping
	public Ram create(@RequestBody Ram ram){
		//System.out.println(ram + "ddddddddddddddddddddddddddddd");
		return ramService.save(ram);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id){
		ramService.deleteById(id);
	}

	@GetMapping("/page/ram")
	public ResponseEntity<Map<String, Object>> findByPublished(
			@RequestParam(defaultValue = "5") int page,
			@RequestParam(defaultValue = "5") int size) {

		try {
			List<Ram> ram = new ArrayList<Ram>();
			Pageable paging = PageRequest.of(page, size);

			Page<Ram> pageTuts = ramReponsitory.findAll(paging);
			ram = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("ram", ram);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//màu

	@GetMapping("/getall")
	public List<Color> getAll(){
		return colorService.findAll();
	}
	@PostMapping("/save")
	public Color createColor(@RequestBody Color color){
		System.out.println(color + "ddddddddddddddddddddddddddddd");
		return colorService.save(color);
	}
	@DeleteMapping("/deleteColor/{idColor}")
	public void deleteColor(@PathVariable("idColor") Integer id){
		colorService.deleteById(id);
	}

	// dung lượnng
	@GetMapping("/getCapacity")
	public List<Capacity> getAllCapacity(){
		return capacityService.findAll();
	}

	@PostMapping("/saveCapacity")
	public Capacity createColor(@RequestBody Capacity capacity){
		return capacityService.save(capacity);
	}

	@PostMapping("/saveFile")
	public void saveFile(@RequestBody Image imagesProduct, MultipartFile imageFile){
		if(!imageFile.isEmpty()){
			String path = application.getRealPath("/");
			System.out.println("path: "+path);
			try {
				imagesProduct.setName(imageFile.getOriginalFilename());
				System.out.println("image: "+imagesProduct.getName());
				String filePath = path+"/images/"+imagesProduct.getName();
				imageFile.transferTo(new File(filePath));
				imageFile=null;
				imagesProduct.setName(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GetMapping("/allImage")
	public List<Image> findallImage(){
		return imageService.findAll();
	}

	@PostMapping("/saveImage")
	public Image createImages(@RequestBody Image images){
		return imageService.save(images);
	}

	@DeleteMapping("/{id}")
	public void deleteImage(@PathVariable("id") Integer id){
		imageService.deleteById(id);
	}



}
