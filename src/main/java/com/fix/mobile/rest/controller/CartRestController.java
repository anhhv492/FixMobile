package com.fix.mobile.rest.controller;

import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/cart")
public class CartRestController {
    Logger LOGGER = Logger.getLogger(CartRestController.class);
    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private CategoryService categoryService;
}
