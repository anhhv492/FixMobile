package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.ProductChangeDTO;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.ProductChangeService;
import com.fix.mobile.service.sendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value= "/rest/admin/productchange")
@CrossOrigin("*")
public class QLProductChangeRestController {
	@Autowired
	private ProductChangeService productChangeService;

	@Autowired
	private  sendMailService mailServices;

	@RequestMapping(value = "/comfirmRequest", method = RequestMethod.POST)
	public void   comfirmRequest(
			@RequestBody List<Integer>  idProductChange){
		for ( Integer  s :  idProductChange) {
			if(s !=null){
				ProductChange product = productChangeService.findByStatus(s);
				product.setStatus(2);
				productChangeService.save(product);
				mailServices.SendEmail("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",s);
				System.out.println("gửi mail thành công");
			}else{
				System.out.println("null lỗi");
			}
		}

	}

	@RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
	public void   cancelRequest(
			@RequestBody List<Integer>  idProductChange){
		for ( Integer  s :  idProductChange) {
			if(s !=null) {
				ProductChange product = productChangeService.findByStatus(s);
				if(product.getStatus()==1){
					product.setStatus(0);
					productChangeService.save(product);
				}else if(product.getStatus()==2){
					System.out.println("không thể hủy ");
				}
			}
		}
	}


}
