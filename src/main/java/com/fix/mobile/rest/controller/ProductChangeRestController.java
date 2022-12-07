package com.fix.mobile.rest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.config.WebConfigrutation;
import com.fix.mobile.dto.ChangeDetailDTO;
import com.fix.mobile.dto.OrderDetailDTO;
import com.fix.mobile.dto.ProductChangeDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.Image;
import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowReactiveWebServerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value= "/rest/productchange")
@CrossOrigin("*")
public class ProductChangeRestController {

	@Autowired private ProductChangeService productChangeSerivce;
	@Autowired private ChangeDetailService changeDetailsService;

	@Autowired private Cloudinary cloud;

	@Autowired private ImageService imageService;
	@Autowired private AccountService accountService;


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
				p.setStatus(0);
				productChangeSerivce.save(p);
				for ( MultipartFile multipartFile :  productchange.getFiles()) {
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
	public void saveRequest(@RequestBody List<String> changeDetails){
		if( changeDetails != null) {
			for (int i = 0; i < changeDetails.size(); i++) {
				changeDetailsService.createChangeDetails(changeDetails.get(i));
			}
		}
	}

	@RequestMapping(value= "/getAll")
	public List<ChangeDetail> list(){
		List<ChangeDetail> listProduct =  changeDetailsService.findAll();
		if(listProduct.isEmpty()){
			return null;
		}
		return listProduct;
	}
}