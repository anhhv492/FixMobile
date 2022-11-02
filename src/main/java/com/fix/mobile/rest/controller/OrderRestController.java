package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.service.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
@CrossOrigin("*")
public class OrderRestController {
    @Autowired
    private AccessoryService accessoryService;
    @GetMapping(value="/cart-accessory/{id}")
    public Accessory findById(@PathVariable("id") Integer id){
        Optional<Accessory> accessory = accessoryService.findById(id);
        if(accessory.isPresent()){
            return accessory.get();
        }
        return null;
    }
}
