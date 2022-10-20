package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Sale;
import com.fix.mobile.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/admin/rest/sale")
public class SaleRestController {

    @Autowired
    SaleService saleSV;

    @RequestMapping("/demo")
    public Sale add(@RequestBody Sale sale){

        System.out.println(sale.toString());
        return saleSV.add(sale);
    }
//    @RequestMapping("demotb")
//    public ResponseEntity<Sale> login(@RequestBody Sale sale) {
//        log.info("Begin login");
//        try {
//            // Thực hiện login
//        } finally {
//            log.info("Done");
//        }
//    }
}
