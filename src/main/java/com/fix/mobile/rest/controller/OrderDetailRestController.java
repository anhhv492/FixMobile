package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.OrderDetailService;
import com.fix.mobile.service.OrderService;
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
    private AccessoryService accessoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping(value="/rest/user/order-detail/{id}")
    public List<OrderDetail> getAllByAccount(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order.get());
        return orderDetails;
    }

}
