package com.fix.mobile.rest.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.dto.ChangeDetailDTO;
import com.fix.mobile.dto.ProductChangeDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.repository.OrderDetailRepository;
import com.fix.mobile.repository.OrderRepository;
import com.fix.mobile.service.*;
import com.fix.mobile.utils.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping(value= "/rest/user/productchange")
@CrossOrigin("*")
public class ProductChangeRestController {

	@Autowired private ProductChangeService productChangeSerivce;
	@Autowired private ChangeDetailService changeDetailsService;
	@Autowired private Cloudinary cloud;
	@Autowired private ImageService imageService;

	@Autowired private AccountService accountService;
	@Autowired private OrderDetailService  orderDetailsService;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value = "/findProductChange/{idDetail}",method = RequestMethod.GET)
	public OrderDetail findByProductChange (@PathVariable("idDetail") Integer idOrderDetails){
			Optional<OrderDetail> orderDetails =  orderDetailsService.findById(idOrderDetails);
			return orderDetails.get();
	}
	// đẩy hóa đơn đổi
	@RequestMapping(value="/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void requestProductChange(
			@ModelAttribute ProductChangeDTO productchange){
		try {
			if(productchange !=null){
				ProductChange p = new ProductChange();
				p.setAccount(productchange.getAccount());
				p.setDateChange(new Date());
				p.setNote(productchange.getNote());
				p.setEmail(productchange.getEmail());
				p.setQuantity(productchange.getQuantity());
				p.setOrderDetail(productchange.getOrderDetail());
				p.setStatus(1);
				productChangeSerivce.save(p);
				OrderDetail orderDetail = orderDetailRepository.findById(productchange.getOrderDetail().getIdDetail()).get();
				orderDetail.setStatus(1);
				orderDetailsService.update(orderDetail,orderDetail.getIdDetail());
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

	// đẩy hóa đơn đổi chi tiết
	@RequestMapping(path = "/saveRequest",method = RequestMethod.POST,
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void saveRequest(@ModelAttribute ChangeDetailDTO changeDetails){
		try {
		    if(changeDetails!=null){
				ChangeDetail change= new ChangeDetail();
				change.setOrderDetail(changeDetails.getOrderDetail());
				changeDetailsService.createChangeDetails(changeDetails.getOrderDetail().getIdDetail());
				Optional<OrderDetail> orderDetails =  orderDetailsService.findById(changeDetails.getOrderDetail().getIdDetail());
				orderDetails.orElseThrow().setStatus(1);
				orderDetailsService.save(orderDetails.get());
			}else;
		}catch (Exception e ){
			e.getMessage();
		}
	}

	@RequestMapping(value= "/getAll", method = RequestMethod.GET)
	public List<ProductChange> listProductChange(){
		List<ProductChange> listProduct =  productChangeSerivce.listProductChange();
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

	// get hóa đơn đổi trả chi tiết
	@RequestMapping(value= "/getPrChangeDetails/{idChange}",  method =  RequestMethod.GET )
	public ChangeDetail listPrChangeDetails(@PathVariable("idChange") ProductChange  idChange) {
		ChangeDetail listPrChangeDetails = changeDetailsService.findPrChangeDetails(idChange);
		if(listPrChangeDetails == null){
			return null;
		}
		return listPrChangeDetails;
	}

	@RequestMapping(value= "/getPrChangeByUser/{username}", method =  RequestMethod.GET)
	public List<ProductChange> findByUser(@RequestParam("username") String user) {
		List<ProductChange> listPrChangeDetails = productChangeSerivce.findByUsername(user);
		return listPrChangeDetails;
	}

	// get ảnh
	@GetMapping(value = "/findImageByPr/{id}")
	public List<Image>   findAllImageByPr(@PathVariable("id") Integer id){
		List<Image> listImage = imageService.findImageByPr(id);
		if(listImage !=null){
			return listImage;
		}
		return null;
	}

}