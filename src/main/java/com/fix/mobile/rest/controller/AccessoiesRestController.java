package com.fix.mobile.rest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.entity.*;
import com.fix.mobile.repository.RamRepository;
import com.fix.mobile.service.*;
import org.apache.log4j.Logger;
import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/staff/accessories")
public class AccessoiesRestController {

	Logger LOGGER = Logger.getLogger(AccessoiesRestController.class);

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

	@Autowired
	private Cloudinary cloud;

//	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public List<Ram> findall(){
		return ramService.findAll();
	}

	@PostMapping
	public Ram create(@RequestBody Ram ram){
		return ramService.save(ram);
	}

	@PostMapping("/delete/{idRam}")
	public void delete(@PathVariable("idRam") Integer id){
		ramService.deleteById(id);
	}


	//màu

	@GetMapping("/getall")
	public List<Color> getAll(){
		return colorService.findAll();
	}
	@PostMapping("/save")
	public Color createColor(@RequestBody Color color){
		return colorService.save(color);
	}
	@PostMapping("/deleteColor/{idColor}")
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

	@PostMapping("/deleteCapacity/{idCapacity}")
	public void deleteCapacity(@PathVariable("idCapacity") Integer id){
		capacityService.deleteById(id);
	}

	@PostMapping("/saveFile")
	public void saveFile(@RequestBody Image imagesProduct, MultipartFile imageFile){
		if(!imageFile.isEmpty()){
			List<String> fileName = new ArrayList<>();
			String path = application.getRealPath("/");
			System.out.println("path: "+path);
			try {
				imagesProduct.setName(imageFile.getOriginalFilename());
				System.out.println("image: "+imagesProduct.getName());
				String filePath = path+"/images/"+imagesProduct.getName();
				imageFile.transferTo(new File(filePath));
				imagesProduct.setName(filePath);
				fileName.add(imagesProduct.getName());
				System.out.println("ksssss"+ fileName);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println( "lỗi"+ e);
			}
		}
	}

	@PostMapping("/upload/file")
	public List<String> list(String folder){
		List<String> filenames = new ArrayList<String>();
		File dir = Paths.get(application.getRealPath("/files/"),folder).toFile();
		if(dir.exists()) {
			File[] files = dir.listFiles();
			for(File file:files) {
				filenames.add(file.getName());
			}
		}
		return filenames;
	}

	@GetMapping("/allImage")
	public List<Image> findallImage(){
		return imageService.findAll();
	}

	@PostMapping("/saveImage")
	public void createImages(@RequestParam("files") MultipartFile[] files){
		 //.save(images);
		Arrays.asList(files).stream().forEach(file -> {
			//storageService.save(file);
			//fileNames.add(file.getOriginalFilename());
			System.out.println(file.getOriginalFilename());
		});
	}

	@PostMapping("/{id}")
	public void deleteImage(@PathVariable("id") Integer id){
		try {
			if(id!=null){
				Optional<Image> images = imageService.findById(id);
				String key = images.get().getName().substring(62,82);
				this.cloud.uploader().destroy(key,
						ObjectUtils.asMap(
								"cloud_name", "dcll6yp9s",
								"api_key", "916219768485447",
								"api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
								"secure", true,
								"folders","c202a2cae1893315d8bccb24fd1e34b816"
						));
			}else;
		}catch (Exception e){
			e.getMessage();
		}
		imageService.deleteById(id);
	}



}
