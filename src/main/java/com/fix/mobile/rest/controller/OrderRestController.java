package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Order;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("*")
public class OrderRestController {
    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @PutMapping(value="/rest/staff/order")
    public Order update(@RequestBody Order order,Principal principal){
        System.out.println("start update order");
        Order orderNew = orderService.findById(order.getIdOrder()).get();
        orderNew.setStatus(order.getStatus());
        Account account = accountService.findByUsername(principal.getName());
        if(account.getRole().getName().equals("ADMIN")||account.getRole().getName().equals("STAFF")){
            orderService.update(orderNew,orderNew.getIdOrder());
            return orderNew;
        }
        return null;
    }
    @GetMapping(value="/rest/staff/order")
    public List<Order> getAll(){

        return orderService.findAll();
    }
    @GetMapping(value="/cart-accessory/{id}")
    public Accessory findById(@PathVariable("id") Integer id){
        Optional<Accessory> accessory = accessoryService.findById(id);
        if(accessory.isPresent()){
            return accessory.get();
        }
        return null;
    }
    @GetMapping(value="/rest/user/order")
    public List<Order> getAllByAccount(Principal principal){
        Account account = accountService.findByUsername(principal.getName());
        List<Order> orders = orderService.findAllByAccount(account);
        return orders;
    }

}
