package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.*;
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
    @GetMapping("/rest/staff/order/detail/imei/add/{idDetail}/{idImeiOld}/{idImeiNew}")
    public List<ImayProduct> addOrderDetailImei(@PathVariable("idDetail") String idDetail,
                                                @PathVariable("idImeiOld") String idImeiOld,
                                                @PathVariable("idImeiNew") String idImeiNew){
        System.out.println("--id: "+idImeiOld+ " id2: "+idDetail+"--"+idImeiNew);
        Integer idDetailInt = Integer.parseInt(idDetail);
        Integer idImeiOldInt = Integer.parseInt(idImeiOld);
        Integer idImeiNewInt = Integer.parseInt(idImeiNew);
        OrderDetail orderDetail = orderDetailService.findById(idDetailInt).get();

        ImayProduct imayProductNew = imeiProductService.findById(idImeiNewInt).get();
        imayProductNew.setStatus(0);
        imayProductNew.setOrderDetail(orderDetail);
        imeiProductService.update(imayProductNew,imayProductNew.getIdImay());

        ImayProduct imayProductOld = imeiProductService.findById(idImeiOldInt).get();
        imayProductOld.setStatus(1);
        imayProductOld.setOrderDetail(null);
        imeiProductService.update(imayProductOld,imayProductOld.getIdImay());

        List<ImayProduct> imayProducts = imeiProductService.findByProductAndStatus(productDT,1);
        return imayProducts;
    }
}
