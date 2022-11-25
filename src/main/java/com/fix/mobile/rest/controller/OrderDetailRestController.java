package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.*;
import com.fix.mobile.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value="/rest/staff/order/detail/{id}")
    public List<OrderDetail> getAllStaffByAccount(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order.get());
        return orderDetails;
    }
    @GetMapping(value="/rest/staff/order/detail/imei/{id}")
    public List<ImayProduct> getImeis(@PathVariable("id") Integer id){
        OrderDetail orderDetail = orderDetailService.findById(id).get();
        List<ImayProduct> imeis = imeiProductService.findByOrderDetail(orderDetail);
        return imeis;
    }
    @GetMapping(value="/rest/user/order/detail/{id}")
    public List<OrderDetail> getAllUserByAccount(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order.get());
        return orderDetails;
    }
}
