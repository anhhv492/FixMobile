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
@RequestMapping(value= "/rest/admin/product")
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
	private Cloudinary cloud;

	@Autowired  private ExcelProducts excelProduct;

	@Autowired private ImayProductService imayService;

	@Autowired private ProductRepository repose;

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
			if(imay !=null){
				for ( String  s :  imay.getName()) {
					ImayProduct i = new ImayProduct();
					i.setName(s);
					i.setProduct(imay.getProduct());
					i.setStatus(1);
					imayService.save(i);
				}
			}else;
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
	@RequestMapping(value = "/findProductByPrice",method = RequestMethod.GET)
	public List<Product> findProductByPrice(){
		List<Product> listproduct = productService.findProductByPrices();
		if(listproduct.isEmpty()){
			return  null;
		}
		return listproduct;
	}


	@RequestMapping("/findproduct/{page}")
	public Page<Product> findProduct(
			@PathVariable ("page") Integer page,
			@RequestBody JsonNode findProcuctAll
	) {
		Pageable paging = PageRequest.of(page, 4);
		List<Product> listPrd = productService.findProduct();
		List<Product> listFindProduct = new ArrayList<>();
		JsonNode listIdCategory = findProcuctAll.get("idCategory");
		JsonNode listIdRam = findProcuctAll.get("idRam");
		JsonNode listIdColer = findProcuctAll.get("idColer");
		JsonNode listIdCapacity = findProcuctAll.get("idCapacity");
		Integer sortMinMax = findProcuctAll.get("sortMinMax").asInt();
		System.out.println(listPrd.size());
		System.out.println(String.valueOf(findProcuctAll.get("search")).replaceAll("\"","")+"hihihi");
		if(listPrd.size()!=0) {
			for (int i = 0; i < listPrd.size(); i++) {
				if (
						listIdCategory.size() == 0 &&
								listIdRam.size() == 0 &&
								listIdCapacity.size() == 0 &&
								listIdColer.size() == 0
				) {
					if (listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
					) {
						listFindProduct = checklist(listFindProduct, listPrd.get(i));
					}
				} else {
					if (listIdCategory.size() != 0) {
						for (int y = 0; y < listIdCategory.size(); y++) {
							if (listIdCategory.get(y).asInt() == listPrd.get(i).getCategory().getIdCategory()
									&& listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							) {
								listFindProduct = checklist(listFindProduct, listPrd.get(i));
							}
						}
					}

					if (listIdRam.size() != 0) {
						for (int y = 0; y < listIdRam.size(); y++) {
							if (listIdRam.get(y).asInt() == listPrd.get(i).getRam().getIdRam()
									&& listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							) {
								listFindProduct = checklist(listFindProduct, listPrd.get(i));
							}
						}
					}

					if (listIdCapacity.size() != 0) {
						for (int y = 0; y < listIdCapacity.size(); y++) {
							if (listIdCapacity.get(y).asInt() == listPrd.get(i).getCapacity().getIdCapacity()
									&& listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							) {
								listFindProduct = checklist(listFindProduct, listPrd.get(i));
							}
						}
					}

					if (listIdColer.size() != 0) {
						for (int y = 0; y < listIdColer.size(); y++) {
							if (listIdColer.get(y).asInt() == listPrd.get(i).getColor().getIdColor()
									&& listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							) {
								listFindProduct = checklist(listFindProduct, listPrd.get(i));
							}
						}
					}
				}
			}
		}
		Page<Product> pageFindProductAll = new PageImpl<>(sortProduct(listFindProduct,sortMinMax), paging, listFindProduct.size());
		System.out.println(pageFindProductAll);
		return pageFindProductAll;
	}
	private List<BigDecimal> getMaxMinPriceProduct(Integer id){
		return productService.getMinMaxPrice(id);
	}

	private List<Product> checklist( List<Product> listcheck, Product check){
		if(listcheck.size()!=0){
			boolean kT= true;
			for (int i=0;i<listcheck.size();i++){
				if(listcheck.get(i).getIdProduct()==check.getIdProduct()){
					kT = false;
				}
			}
			if(kT==true){
				listcheck.add(check);
			}
		}
		else{
			listcheck.add(check);
		}
		return listcheck;
	}
	private List<Product> sortProduct(List<Product> listPRD,Integer check) {
//		BigDecimal a = new BigDecimal(String.valueOf(listPRD.get(0).getPrice()));
		if(listPRD.size()==0){
			return listPRD;
		}else {
			Comparator<Product>  MinMax = new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					return o1.getPrice().compareTo(o2.getPrice());
				}
			};
			Comparator<Product> MaxMin = new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					return o2.getPrice().compareTo(o1.getPrice());
				}
			};
			if(check == 0){
				Collections.sort(listPRD, MinMax);
			}else{
				Collections.sort(listPRD, MaxMin);
			}
			return listPRD;
		}
	}
	@RequestMapping("/getallproduct")
	public List<ProductDetailDTO> getallProduct(){
		List<ProductDetailDTO> listgetAllProduct= new ArrayList<>();
		for (int x: productService.getlistDetailProductCategory()
			 ) {
			listgetAllProduct.add(getDetailProduct(x));
		}
		return listgetAllProduct;
	}
	@RequestMapping("/detailproduct/{idcate}")
	public ProductDetailDTO getDetailProduct(
			@PathVariable("idcate")Integer id
	){
		List<Ram> listRam=new ArrayList<>();
		List<Capacity> listCapa=new ArrayList<>();
		List<Color> listColor=new ArrayList<>();
		for (int x: productService.getlistDetailProductRam(id)
			 ) {
			listRam.add(ramService.findById(x).get());
		}for (int x: productService.getlistDetailProductCapacity(id)
			 ) {
			listCapa.add(capacityService.findById(x).get());
		}for (int x: productService.getlistDetailProductColor(id)
			 ) {
			listColor.add(colorService.findById(x).get());
		}
		ProductDetailDTO detailProduct=new ProductDetailDTO();
		detailProduct.setId(id);
		detailProduct.setName(categoryService.findById(id).get().getName());
		detailProduct.setRam(listRam);
		detailProduct.setCapa(listCapa);
		detailProduct.setColor(listColor);
		detailProduct.setPriceMin(getMaxMinPriceProduct(id).get(0));
		detailProduct.setPriceMax(getMaxMinPriceProduct(id).get(1));
		return detailProduct;
	}
	@RequestMapping("/getdetailproduct/{idCapa}/{idRam}/{idColor}")
	public Product getdetailProduct(
			@PathVariable("idCapa")Integer idCapa,
			@PathVariable("idRam")Integer idRam,
			@PathVariable("idColor")Integer idColor
	){
		return productService.getdeTailPrd(idCapa,idRam,idColor);
	}
	@RequestMapping("/checkprd/{idCapa}/{idRam}/{idColor}")
	public CheckProductDTO checkPrd(
			@PathVariable("idCapa")Integer idCapa,
			@PathVariable("idRam")Integer idRam,
			@PathVariable("idColor")Integer idColor
	){
		List<Integer> checkCapa = new ArrayList<>();
		List<Integer> checkRam = new ArrayList<>();
		List<Integer> checkColor = new ArrayList<>();
		if(idCapa==0 && idRam==0 && idColor!=0){
			if(productService.ramcheckRamAndCapa(idColor).size()==0){
				checkRam=null;
			}else{
				for (int x:productService.ramcheckRamAndCapa(idColor)
					 ) {
					checkRam.add(x);
				}
			}
			if(productService.CapacheckRamAndCapa(idColor).size()==0){
				checkCapa=null;
			}else{
				for (int x:productService.CapacheckRamAndCapa(idColor)
					 ) {
					checkCapa.add(x);
				}
			}
		}
		if(idCapa==0 && idRam!=0 && idColor!=0){
			if(productService.checkCapa(idRam,idColor).size()==0){
				checkCapa=null;
			}else{
				for (int x:productService.checkCapa(idRam,idColor)
					 ) {
					checkCapa.add(x);
				}
			}

		}

		if(idCapa!=0 && idRam==0 && idColor==0){
			if(productService.ramcheckRamAndColor(idCapa).size()==0){
				checkRam=null;
			}else{
				for (int x:productService.ramcheckRamAndColor(idCapa)
					 ) {
					checkRam.add(x);
				}
			}
			if(productService.colorcheckRamAndColor(idCapa).size()==0){
				checkColor=null;
			}else{
				for (int x:productService.colorcheckRamAndColor(idCapa)
					 ) {
					checkColor.add(x);
				}
			}
		}
		if(idCapa!=0 && idRam!=0 && idColor==0){
			if(productService.checkColor(idRam,idCapa).size()==0){
				checkColor=null;
			}else{
				for (int x:productService.checkColor(idRam,idCapa)
					 ) {
					checkColor.add(x);
				}
			}

		}

		if(idCapa==0 && idRam!=0 && idColor==0){
			if(productService.ColorcheckColorAndCapa(idRam).size()==0){
				checkColor=null;
			}else{
				for (int x:productService.ColorcheckColorAndCapa(idRam)
					 ) {
					checkColor.add(x);
				}
			}
			if(productService.CapacheckColorAndCapa(idRam).size()==0){
				checkCapa=null;
			}else{
				for (int x:productService.CapacheckColorAndCapa(idRam)
					 ) {
					checkCapa.add(x);
				}
			}
		}
		if(idCapa!=0 && idRam==0 && idColor!=0){
			if(productService.checkRam(idColor,idCapa).size()==0){
				checkRam=null;
			}else{
				for (int x:productService.checkRam(idColor,idCapa)
					 ) {
					checkRam.add(x);
				}
			}

		}
		CheckProductDTO check = new CheckProductDTO();
		check.setCheckCapa(checkCapa);
		check.setCheckColor(checkColor);
		check.setCheckRam(checkRam);
		return check;
	}

}
