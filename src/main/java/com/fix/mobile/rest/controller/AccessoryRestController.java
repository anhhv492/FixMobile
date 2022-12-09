package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.AccessoryDTO;
import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.helper.ExcelHelper;
import com.fix.mobile.repository.AccessoryRepository;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
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
	private AccessoryRepository accessoryRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ExcelHelper excelHelper;
	//findAll accessory
	@GetMapping
	public List<Accessory> findAll(){
		return accessoryService.findAll();
	}
	//findall accessory pageable
	@GetMapping(value="/page/{page}")
	public List<Accessory> findAllPageable(@PathVariable("page") Optional<Integer> page){
		Pageable pageable = PageRequest.of(page.get(), 5);
		List<Accessory> accessories = accessoryService.findAll(pageable).getContent();
		LOGGER.info("findAllPageable: "+accessories.size());
		LOGGER.info("page: "+page);
		return accessories;
	}
	@GetMapping(value="/{id}")
	public Accessory findById(@PathVariable("id") Integer id){
		Optional<Accessory> accessory = accessoryService.findById(id);
		if(accessory.isPresent()){
			return accessory.get();
		}
		return null;
	}
	//find category by accessory
	@GetMapping("/cate")
	public List<Category> findByCate(){
		return categoryService.findByType();
	}
	//find accessory by category
	@GetMapping("/cate/{id}")
	public List<Accessory> findByCateId(@PathVariable("id") Integer id){
		Optional<Category> cate = categoryService.findById(id);
		if(cate.isEmpty()){
			return null;
		}
		List<Accessory> accessories = accessoryService.findByCategoryAndStatus(cate);
		return accessories;
	}

	@PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Accessory save(@ModelAttribute AccessoryDTO accessoryDTO) throws Exception{
		LOGGER.info("save: "+accessoryDTO);
//		googleDriveService.upLoadFile(accessory.getImage(), accessory.getImage(), "image/png");
		return accessoryService.save(accessoryDTO);
	}
	//change status accessory
	@PutMapping("/change/{id}")
	public Accessory changeStatus(@PathVariable("id") Integer id){
	    Accessory accessory = accessoryService.findById(id).get();
		if(accessory.getStatus() == true){
			accessory.setStatus(false);
		}else{
			accessory.setStatus(true);
		}
		LOGGER.info("change status: "+id);
		return accessoryRepository.save(accessory);
	}
	//update accessory
	@PostMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Accessory update(@RequestParam("id") Integer id, @ModelAttribute AccessoryDTO accessoryDTO){
		LOGGER.info("update: "+accessoryDTO);
		return accessoryService.update(id, accessoryDTO);
	}

//	@PostMapping("/read-excel")
//	public Boolean readExcel(@PathVariable("file") MultipartFile file) throws Exception{
//		System.out.println("readExcel");
//		try{
//			Boolean checkExcel = excelHelper.readExcel(file);
//			return checkExcel;
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}

	@RequestMapping("/getdatasale/{page}")
	public Page<Accessory> getDataShowSale(
			@PathVariable ("page") Integer page,
			@RequestParam ("share") String share
	){
		if(null == share||"undefined".equals(share)){
			share="";
		}
		return accessoryService.getByPage(page,5,share);
	}
}
