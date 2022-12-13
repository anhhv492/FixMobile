package com.fix.mobile.rest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.dto.ChangeDetailDTO;
import com.fix.mobile.dto.ProductChangeDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping(value= "/rest/productchange")
@CrossOrigin("*")
public class ProductChangeRestController {

	@Autowired private ProductChangeService productChangeSerivce;
	@Autowired private ChangeDetailService changeDetailsService;
	@Autowired private Cloudinary cloud;
	@Autowired private ImageService imageService;
	@Autowired private AccountService accountService;

	@Autowired private OrderDetailService  orderDetailsService;
	@RequestMapping(value = "/findProductChange/{idDetail}",method = RequestMethod.GET)
	public OrderDetail findByProductChange (@PathVariable("idDetail") Integer idOrderDetails){
			Optional<OrderDetail> orderDetails =  orderDetailsService.findById(idOrderDetails);
			return orderDetails.get();
	}
	@RequestMapping(value="/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void requestProductChange(
			@ModelAttribute ProductChangeDTO productchange){
		try {
			if(productchange !=null){
				Account acc= accountService.findByUsername("nam");
				ProductChange p = new ProductChange();
				p.setAccount(acc);
				p.setDateChange(new Date());
				p.setNote(productchange.getNote());
				p.setEmail(productchange.getEmail());
				p.setStatus(1);
				productChangeSerivce.save(p);
				for (MultipartFile multipartFile :  productchange.getFiles()) {
					Map r = this.cloud.uploader().upload(multipartFile.getBytes(),
							ObjectUtils.asMap(
									"cloud_name", "dcll6yp9s",
									"api_key", "916219768485447",
									"api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
									"secure", true,
									"folders","c202a2cae1893315d8bccb24fd1e34b816"
							));
					Image image = new Image();
					image.setName(r.get("secure_url").toString());
					image.setProductChange(p);
					imageService.save(image);
				}
			}else System.out.println("null");
		}catch (Exception e){
			e.getMessage();
		}
	}

	@RequestMapping("/saveRequest")
	public void saveRequest(@ModelAttribute ChangeDetailDTO changeDetails){
		try {
		    if(changeDetails!=null){
				for ( String  s :  changeDetails.getImaysp()) {
					ChangeDetail change= new ChangeDetail();
					change.setImaysp(s);
					change.setProduct(changeDetails.getProduct());
					change.setOrderDetail(change.getOrderDetail());
					changeDetailsService.createChangeDetails(changeDetails.getOrderDetail().getIdDetail(),s);
				}
			}else;
		}catch (Exception e ){
			e.getMessage();
			e.printStackTrace();
		}
	}

	@RequestMapping(value= "/getAll", method = RequestMethod.GET)
	public List<ProductChange> listProductChange(){
		List<ProductChange> listProduct =  productChangeSerivce.findAllProductChange();
		if(listProduct.isEmpty()){
			return null;
		}Comparator<ProductChange> comparator =  new Comparator<ProductChange>() {
			@Override
			public int compare(ProductChange o1, ProductChange o2) {
				return o2.getIdChange().compareTo(o1.getIdChange());
			}
		};
		Collections.sort(listProduct,comparator);
		return listProduct;
	}

	@RequestMapping(value= "/getPrChangeDetails/{idChange}")
	public List<ChangeDetail> listPrChangeDetails(@PathVariable("idChange") String id) {
		List<ChangeDetail> listPrChangeDetails = changeDetailsService.findPrChangeDetails(id);
		return listPrChangeDetails;
	}
	@RequestMapping(value= "/getPrChangeByUser/{username}", method =  RequestMethod.GET)
	public List<ProductChange> findByUser(@RequestParam("username") String user) {
		List<ProductChange> listPrChangeDetails = productChangeSerivce.findByUsername(user);
		return listPrChangeDetails;
	}

}