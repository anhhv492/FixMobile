package com.fix.mobile.rest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.CheckProductDTO;
import com.fix.mobile.dto.ImayProductDTO;
import com.fix.mobile.dto.ImeiProductResponDTO;
import com.fix.mobile.dto.ProductDetailDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.helper.ExcelProducts;
import com.fix.mobile.payload.SaveProductRequest;
import com.fix.mobile.repository.ImayProductRepository;
import com.fix.mobile.repository.ProductRepository;
import com.fix.mobile.service.*;
import org.apache.log4j.Logger;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value= "/rest/staff/product")
@CrossOrigin("*")
@Component
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
	private ImayProductService imayProductService;

	@Autowired
	private Cloudinary cloud;

	@Autowired  private ExcelProducts excelProduct;

	@Autowired private ImayProductService imayService;

	@Autowired private ProductRepository repose;
	@Autowired
	private ImayProductRepository imayProductRepository;

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

	private String generationName(SaveProductRequest prd){
		String name="";
		List<Category> listCate =categoryService.findAll();
		List<Capacity> listCapa = capacityService.findAll();
		List<Color> listColor = colorService.findAll();
		List<Ram> listRam = ramService.findAll();
		for (Category cate:listCate
			 ) {
			if(prd.getCategory().getIdCategory()==cate.getIdCategory()){
				name+= cate.getName();
			}
		}
		for (Capacity capa:listCapa
		) {
			if(prd.getCapacity().getIdCapacity()==capa.getIdCapacity()){
				name+= " Dung Lượng " + capa.getName();
			}
		}
		for (Ram ram:listRam
		) {
			if(prd.getRam().getIdRam()==ram.getIdRam()){
				name+=" RAM " + ram.getName();
			}
		}
		for (Color color:listColor
		) {
			if(prd.getColor().getIdColor() == color.getIdColor()){
				name+=" Màu " +color.getName();
			}
		}
		return name;
	}

	@RequestMapping(path = "/saveProduct", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void save(@ModelAttribute SaveProductRequest saveProductRequest){
		Date date = new Date();
		Product p = new Product();
		p.setName(generationName(saveProductRequest));
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

	@RequestMapping(value = "/delete", method = POST)
	public void delete(@RequestParam("id") Integer id){
		Optional<Product> p = productService.findById(id);
		if(p!=null){
			p.orElseThrow().setStatus(0);
			productService.save(p.get());

		}else{
			System.out.println("không tồn tại");
		}
	}

	@RequestMapping(path="/updateProduct", method = POST)
	public void update(@RequestParam("id") Integer id ,
					   @ModelAttribute SaveProductRequest saveProductRequest) {
	        Optional<Product> p = productService.findById(id);
		try {
			List<ImayProduct> imayProducts = null;
			ImayProduct imayProduct = null;
			if(saveProductRequest.getStatus()==0){
				imayProducts= imayProductRepository.findAllByProductAndStatus(p.get(),1);
				if(imayProducts!=null) {
					for (int i = 0; i < imayProducts.size(); i++) {
						imayProduct = imayProducts.get(i);
						imayProduct.setStatus(4);
						imayService.update(imayProduct, imayProduct.getIdImay());
					}
				}
			}else{
				imayProducts= imayProductRepository.findAllByProductAndStatus(p.get(),4);
				if(imayProducts!=null){
					for(int i=0;i<imayProducts.size();i++){
						imayProduct = imayProducts.get(i);
						imayProduct.setStatus(1);
						imayService.update(imayProduct,imayProduct.getIdImay());
					}
				}
			}
			if(p!=null){
				p.orElseThrow().setName(saveProductRequest.getName());
				p.orElseThrow().setNote(saveProductRequest.getNote());
				p.orElseThrow().setSize(saveProductRequest.getSize());
				p.orElseThrow().setCategory(saveProductRequest.getCategory());
				p.orElseThrow().setColor(saveProductRequest.getColor());
				p.orElseThrow().setRam(saveProductRequest.getRam());
				p.orElseThrow().setCapacity(saveProductRequest.getCapacity());
				p.orElseThrow().setCamera(saveProductRequest.getCamera());
				p.orElseThrow().setStatus(saveProductRequest.getStatus());
				p.orElseThrow().setPrice(saveProductRequest.getPrice());
				productService.save(p.get());

			}else{
				throw new StaleStateException("Bản ghi này không tòn tại");
			}
			System.out.println("Uploaded the files successfully: " + saveProductRequest.getFiles().size());

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}


	}


	@PostMapping("/readExcel")
	public Boolean readExcel(@PathParam("file") MultipartFile file) throws Exception{
		Boolean checkExcel= excelProduct.readExcel(file);
		return checkExcel;
	}
	// imay product
	@PostMapping("/saveImay")
	public ImayProduct saveImay(@ModelAttribute ImayProduct  imay){
		try {
			List<ImayProduct> list = imayService.findAll();
			Boolean aBoolean=false;
			ImayProduct im=null;
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					if(imay.getName().equals(list.get(i).getName())){
						aBoolean=false;
						return null;
					}else{
						aBoolean=true;
						im = new ImayProduct();
					}
				}
			}
			if(aBoolean==true){
				im.setName(imay.getName());
				im.setProduct(imay.getProduct());
				im.setStatus(1);
				imayService.save(im);
				return imay;
			}
		}catch (Exception e ){
			e.getMessage();
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/getImeiByName")
	public ImayProduct getImeiByname(@RequestParam("name") String name){
		return imayProductService.findImeiByName(name);
	}

	@PostMapping("/readExcelImay")
	public Boolean readExcelImay(@PathParam("file") MultipartFile file) throws Exception{
		Boolean checkExcel= excelProduct.readExcelImay(file);
		return checkExcel;
	}

	// get top sp
	@GetMapping(value ="/findByProduct")
	public List<Product> findByproduct() {
		List<Product> listproduct = productService.findByProductLimit();
		if (listproduct.isEmpty()){
			System.out.println("null");
		}
		return listproduct;
	}
	//get giá có sẵn 200k
	@GetMapping(value ="/findByPriceExits")
	public List<Product> findByPriceExits() {
		return productService.findByProductLitmitPrice();
	}

	@RequestMapping("/getdatasale/{page}")
	public Page<Product> getDataShowSale(
			@PathVariable ("page") Integer page,
			@RequestParam ("share") String share
	){
		if(null == share||"undefined".equals(share)){
			share="";
		}
		return productService.findShowSale(page,5,share);
	}

	@GetMapping(value ="/findByProductCode")
	public Optional<Product> findByProductCode(@RequestParam("id") Integer productCode) {
		Optional<Product> product = null;
		if (productCode == null) ;
			product = productService.findById(productCode);
		return product;
	}

	@GetMapping("/pageImei")
	public Page<ImeiProductResponDTO> pageImei (
			@RequestParam(name = "page" , defaultValue = "1") int page,
			@RequestParam(name = "size" , defaultValue = "10") int size,
			@RequestParam(name = "status", defaultValue = "1") Integer status
	){
		Pageable pageable = PageRequest.of(page - 1 , size);
		return imayService.findAll(pageable, status);
	}

	@PostMapping("/deleteImeiById")
	public void deleteImei(@RequestParam("id") Integer id){
		imayService.deleteById(id);
	}

	//findBy product price



	@RequestMapping("/findproduct/{page}")
	public Page<ProductDetailDTO> findProduct(
			@PathVariable ("page") Integer page,
			@RequestBody JsonNode findProcuctAll
	) {
		return productService.getByPage(page,9,findProcuctAll);
	}
	@RequestMapping("/detailproduct/{idcate}")
	public ProductDetailDTO getDetailProduct(
			@PathVariable("idcate")Integer id
	){
		return productService.getDetailProduct(id);
	}
	@RequestMapping("/getdetailproduct/{idCapa}/{idRam}/{idColor}")
	public Product getdetailProduct(
			@PathVariable("idCapa")Integer idCapa,
			@PathVariable("idRam")Integer idRam,
			@PathVariable("idColor")Integer idColor
	){
		return productService.getdeTailPrd(idCapa,idRam,idColor);
	}

}
