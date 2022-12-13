package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.ProductChangeDTO;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.ProductChangeService;
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

	@RequestMapping(value = "/comfirmRequest", method = RequestMethod.POST)
	public void   comfirmRequest(
			@RequestBody List<Integer>  idProductChange){
		for ( Integer  s :  idProductChange) {
		    ProductChange product = productChangeService.findByStatus(s);
			product.setStatus(2);
			productChangeService.save(product);
		}
	}

	@RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
	public void   cancelRequest(
			@RequestBody List<Integer>  idProductChange){
		for ( Integer  s :  idProductChange) {
			ProductChange product = productChangeService.findByStatus(s);
			product.setStatus(0);
			productChangeService.save(product);
		}
	}


}
