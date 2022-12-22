package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.ProductChangeDTO;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.Image;
import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.ImageService;
import com.fix.mobile.service.OrderDetailService;
import com.fix.mobile.service.ProductChangeService;
import com.fix.mobile.service.sendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value= "/rest/staff/productchange")
@CrossOrigin("*")
public class QLProductChangeRestController {
	@Autowired
	private ProductChangeService productChangeService;

	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	private  sendMailService mailServices;



	// xác nhận đổi chờ kiểm tra
	@RequestMapping(value = "/comfirmRequest", method = RequestMethod.POST)
	public void   comfirmRequest(
			@RequestBody List<Integer>  idProductChange){
		OrderDetail orderDetail=null;
		for ( Integer  s :  idProductChange) {
			ProductChange product = productChangeService.findByStatus(s);
			if(s !=null && product.getStatus()== 1){
				product.setStatus(2);
				productChangeService.save(product);
				orderDetail=product.getOrderDetail();
				orderDetail.setStatus(2);
				orderDetailService.update(orderDetail,orderDetail.getIdDetail());
				mailServices.SendEmail("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",s);
				System.out.println("gửi mail thành công");
			}else if(s !=null && product.getStatus()== 2){
				product.setStatus(3);
				orderDetail.setStatus(3);
				orderDetailService.update(orderDetail,orderDetail.getIdDetail());
				productChangeService.save(product);
			}
			else{
				System.out.println("null lỗi");
			}
		}

	}

	// hủy đổi trả yêu cầu
	@RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
	public void   cancelRequest(
			@RequestBody List<Integer>  idProductChange){
		for ( Integer  s :  idProductChange) {
			if(s !=null) {
				ProductChange product = productChangeService.findByStatus(s);
				if(product.getStatus()==1){
					product.setStatus(4);
					productChangeService.save(product);
				}else if(product.getStatus()==2){
					System.out.println("không thể hủy ");
				}
			}
		}
	}




}
