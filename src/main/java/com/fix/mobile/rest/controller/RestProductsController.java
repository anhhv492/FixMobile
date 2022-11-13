package com.fix.mobile.rest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.dto.ImayProductDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.helper.ExcelProducts;
import com.fix.mobile.payload.SaveProductRequest;
import com.fix.mobile.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.websocket.server.PathParam;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value= "/rest/admin/product")
public class RestProductsController {

	Logger LOGGER = Logger.getLogger(RestProductsController.class);

	@Autowired
	private ProductService productService;
	@Autowired
	private RamService ramService;
	@Autowired
	private ColorService colorService;

	@Autowired
	private CapacityService capacityService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private Cloudinary cloud;

	@Autowired  private ExcelProducts excelProduct;

	@Autowired private ImayProductService imayService;


	@GetMapping("/getAllRam")
	public List<Ram> findAllRam(){
		return ramService.findAll();
	}

	@GetMapping("/getAllCapacity")
	public List<Capacity> findAllCapacity(){
		return capacityService.findAll();
	}
	@GetMapping("/getAllColor")
	public List<Color> findAllColor(){
		return colorService.findAll();
	}
	// danh mục
	@GetMapping("/category")
	public List<Category> findByCate(){
		return categoryService.findByTypeSP();
	}

	@GetMapping("/getAll")
	public List<Product> findAll(){
		return productService.findAll();
	}


	public List<Product> findAllPageable(@PathVariable("page") Optional<Integer> page){
		Pageable pageable = PageRequest.of(page.get(), 5);
		List<Product> products = productService.findAll(pageable).getContent();
		return products;
  }
  
	@GetMapping(value="/page/pushedlist")
	public ResponseEntity<Map<String, Object>> findByPublished(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			List<Product> product = new ArrayList<>();
			Pageable paging = PageRequest.of(page, size);
			Page<Product> pageTuts = productService.getAll(paging);
			product = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("list", product);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/saveProduct", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void save(@ModelAttribute SaveProductRequest saveProductRequest){
		Date date = new Date();
		Product p = new Product();
		p.setName(saveProductRequest.getName());
		p.setNote(saveProductRequest.getNote());
		p.setSize(saveProductRequest.getSize());
		p.setCategory(saveProductRequest.getCategory());
		p.setColor(saveProductRequest.getColor());
		p.setCreateDate(date);
		p.setRam(saveProductRequest.getRam());
		p.setCapacity(saveProductRequest.getCapacity());
		p.setCamera(saveProductRequest.getCamera());
		p.setStatus(saveProductRequest.getStatus());
		p.setPrice(saveProductRequest.getPrice());
		productService.save(p);
		try {
			System.out.println("Uploaded the files successfully: " + saveProductRequest.getFiles().size());
			for ( MultipartFile multipartFile :  saveProductRequest.getFiles()) {
				Map r = this.cloud.uploader().upload(multipartFile.getBytes(),
						ObjectUtils.asMap(
								"cloud_name", "dcll6yp9s",
								"api_key", "916219768485447",
								"api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
								"secure", true,
								"folders","c202a2cae1893315d8bccb24fd1e34b816"
						));
				Image i = new Image();
				i.setName(r.get("secure_url").toString());
				i.setProduct(p);
				imageService.save(i);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

 	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id){
		productService.deleteById(id);
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable("id") Integer id, @RequestBody Product product){
		return productService.update(product,id);
	}

@GetMapping(value="/page/{page}")
	public Page<Product> findAllPage(@PathVariable("page") Integer page){
		Page<Product> pageProduct = productService
				.getByPage(page, 5,0);
		return pageProduct;
   }
   
	@PostMapping("/readExcel")
	public Boolean readExcel(@PathParam("file") MultipartFile file) throws Exception{
		Boolean checkExcel= excelProduct.readExcel(file);
		return checkExcel;
	}
	// imay product
	@PostMapping("/saveImay")
	public void saveImay(@ModelAttribute ImayProductDTO  imay){
		try {
			for ( String  s :  imay.getName()) {
				ImayProduct i = new ImayProduct();
				i.setName(s);
				i.setProduct(imay.getProduct());
				i.setStatus(1);
				imayService.save(i);
			}
		}catch (Exception e ){
			e.getMessage();
			e.printStackTrace();
		}
	}

	@PostMapping("/readExcelImay")
	public Boolean readExcelImay(@PathParam("file") MultipartFile file) throws Exception{
		Boolean checkExcel= excelProduct.readExcelImay(file);
		return checkExcel;
	}

	// get top sp
	@GetMapping("/findByProduct")
	public List<Product> findByproduct() {
		List<Product> listproduct = productService.findByProductLimit();
		if (listproduct.isEmpty()){
			System.out.println("null");
		}
		return listproduct;
	}
	//get giá có sẵn 200k
	@GetMapping("/findByPriceExits")
	public List<Product> findByPriceExits() {
		return productService.findByProductLitmitPrice();
	}


}
