package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.*;
import com.fix.mobile.repository.ImayProductRepository;
import com.fix.mobile.repository.ProductChangeRepository;
import com.fix.mobile.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class OrderDetailRestController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ImayProductService imeiProductService;
    @Autowired
    private ProductService productService;
    Product productDT = null;
    @Autowired
    private ImayProductRepository imayProductRepository;
    @Autowired
    private ProductChangeRepository productChangeRepository;
    @Autowired
    private ProductChangeService productChangeService;

    @GetMapping(value="/rest/staff/order/detail/{id}")
    public List<OrderDetail> getAllStaffByAccount(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order.get());
        return orderDetails;
    }
    @GetMapping(value="/rest/staff/order/detail/imei2/{id}")
    public List<ImayProduct> getImeis(@PathVariable("id") Integer id){
        OrderDetail orderDetail = orderDetailService.findById(id).get();
        List<ImayProduct> imeis = imeiProductService.findByOrderDetail(orderDetail);
        for (ImayProduct imei: imeis) {
            System.out.println(imei.getName());
        }
        return imeis;
    }
    @GetMapping(value="/rest/user/order/detail/{id}")
    public List<OrderDetail> getAllUserByAccount(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order.get());
        return orderDetails;
    }

    @GetMapping("/rest/staff/order/detail/imei/{id}")
    public List<ImayProduct> getImei(@PathVariable("id") Integer id){
        productDT = productService.findById(id).get();
        List<ImayProduct> imayProducts = imeiProductService.findByProductAndStatus(productDT,1);
        return imayProducts;

    }
    @GetMapping("/rest/staff/order/detail/imei/add/{idDetail}/{idImeiNew}")
    public List<ImayProduct> addOrderDetailImei(@PathVariable("idDetail") String idDetail,
                                                @PathVariable("idImeiNew") String idImeiNew){
        System.out.println("--id: "+idImeiNew+ " id2: "+idDetail+"--");
        try{
            ProductChange productChange = null;
            Integer idDetailInt = Integer.parseInt(idDetail);
            Integer idImeiNewInt = Integer.parseInt(idImeiNew);
            OrderDetail orderDetail = orderDetailService.findById(idDetailInt).get();
            ImayProduct imayProductNew = imeiProductService.findById(idImeiNewInt).get();
            imayProductNew.setStatus(0);
            imayProductNew.setOrderDetail(orderDetail);
            imeiProductService.update(imayProductNew,imayProductNew.getIdImay());
            if(orderDetail.getOrder().getStatus() == 3){
                orderDetail.setStatus(3);
                orderDetailService.update(orderDetail,orderDetail.getIdDetail());
                productChange = productChangeRepository.findByOrderDetail(orderDetail);
                productChange.setStatus(3);
                productChangeService.update(productChange,productChange.getIdChange());
            }
            List<ImayProduct> imayProducts = imeiProductService.findByProductAndStatus(imayProductNew.getProduct(),1);
            return imayProducts;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/rest/staff/order/detail/imei/remove/{id}")
    public ImayProduct changeImei(@PathVariable("id") Integer idImei){
        ImayProduct imayProduct = imeiProductService.findById(idImei).get();
        imayProduct.setStatus(1);
        if(imayProduct.getOrderDetail().getStatus() == 2||imayProduct.getOrderDetail().getStatus() == 3){
            imayProduct.setStatus(3);
            imayProduct.setOrderDetail(null);
            imeiProductService.update(imayProduct,imayProduct.getIdImay());
            return imayProduct;
        }else{
            imayProduct.setOrderDetail(null);
            imeiProductService.update(imayProduct,imayProduct.getIdImay());
            return imayProduct;
        }
    }
}
