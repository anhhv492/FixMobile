package com.fix.mobile.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.accessory.AccessoryDTO;
import com.fix.mobile.dto.accessory.AccessoryResponDTO;
import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Product;
import com.fix.mobile.helper.ExcelHelper;
import com.fix.mobile.repository.AccessoryRepository;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

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
	public AccessoryResponDTO save(
			@ModelAttribute AccessoryDTO accessoryDTO) throws Exception{
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
	public AccessoryResponDTO update(@RequestParam("id") Integer id, @ModelAttribute AccessoryDTO accessoryDTO){
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
	@RequestMapping("/findaccessory/{page}")
	public Page<Accessory> findProduct(
			@PathVariable ("page") Integer page,
			@RequestBody JsonNode findProcuctAll
	) {
		Pageable paging = PageRequest.of(page, 9);
		BigDecimal priceMin;
		BigDecimal priceMax;
		if(String.valueOf(findProcuctAll.get("priceSalemin")).replaceAll("\"","").equals("")){
			priceMin = new BigDecimal(String.valueOf(getMaxMinPriceAccessory().get(0)));
		}else {
			priceMin = new BigDecimal(String.valueOf(findProcuctAll.get("priceSalemin")).replaceAll("\"", ""));
		}
		if(String.valueOf(findProcuctAll.get("priceSalemax")).replaceAll("\"","").equals("")){
			priceMax = new BigDecimal(String.valueOf(getMaxMinPriceAccessory().get(1)));
		}else {
			priceMax = new BigDecimal(String.valueOf(findProcuctAll.get("priceSalemax")).replaceAll("\"",""));
		}
		List<Accessory> listASSR = accessoryService.findAccessory();
		List<Accessory> listFindAccessory = new ArrayList<>();
		JsonNode listIdCategory = findProcuctAll.get("idCategory");
		JsonNode listIdColer = findProcuctAll.get("idColer");
		Integer sortMinMax = findProcuctAll.get("sortMinMax").asInt();
		if(listASSR.size()!=0){
			for (int i = 0; i < listASSR.size(); i++) {
				if (
						listIdCategory.size() == 0 &&
								listIdColer.size() == 0
				) {
					if (listASSR.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							&& listASSR.get(i).getPrice().compareTo(priceMin) >= 0
							&& listASSR.get(i).getPrice().compareTo(priceMax) <= 0
					) {
						listFindAccessory = checklist(listFindAccessory, listASSR.get(i));
					}
				} else {
					if (listIdCategory.size() != 0) {
						for (int y = 0; y < listIdCategory.size(); y++) {
							if (listIdCategory.get(y).asInt() == listASSR.get(i).getCategory().getIdCategory()
									&& listASSR.get(i).getPrice().compareTo(priceMin) >= 0
									&& listASSR.get(i).getPrice().compareTo(priceMax) <= 0
									&& listASSR.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							) {
								listFindAccessory = checklist(listFindAccessory, listASSR.get(i));
							}
						}
					}

					if (listIdColer.size() != 0) {
						for (int y = 0; y < listIdColer.size(); y++) {
							if (listIdColer.get(y).asInt() == listASSR.get(i).getColor().getIdColor()
									&& listASSR.get(i).getPrice().compareTo(priceMin) >= 0
									&& listASSR.get(i).getPrice().compareTo(priceMax) <= 0
									&& listASSR.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
							) {
								listFindAccessory = checklist(listFindAccessory, listASSR.get(i));
							}
						}
					}
				}
			}

		}
		Page<Accessory> pageFindAccessoryAll = new PageImpl<>(sortProduct(listFindAccessory,sortMinMax), paging, listFindAccessory.size());
		return pageFindAccessoryAll;
	}
	private List<BigDecimal> getMaxMinPriceAccessory(){
		return accessoryService.getMinMaxPrice();
	}

	private List<Accessory> checklist( List<Accessory> listcheck, Accessory check){
		if(listcheck.size()!=0){
			boolean kT= true;
			for (int i=0;i<listcheck.size();i++){
				if(listcheck.get(i).getIdAccessory()==check.getIdAccessory()){
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
	private List<Accessory> sortProduct(List<Accessory> listASSR,Integer check) {
//		BigDecimal a = new BigDecimal(String.valueOf(listASSR.get(0).getPrice()));
		if(listASSR.size()==0){
			return listASSR;
		}else {
			Comparator<Accessory> MinMax = new Comparator<Accessory>() {
				@Override
				public int compare(Accessory o1, Accessory o2) {
					return o1.getPrice().compareTo(o2.getPrice());
				}
			};
			Comparator<Accessory> MaxMin = new Comparator<Accessory>() {
				@Override
				public int compare(Accessory o1, Accessory o2) {
					return o2.getPrice().compareTo(o1.getPrice());
				}
			};
			if(check == 0){
				Collections.sort(listASSR, MinMax);
			}else{
				Collections.sort(listASSR, MaxMin);
			}
			return listASSR;
		}
	}
}
