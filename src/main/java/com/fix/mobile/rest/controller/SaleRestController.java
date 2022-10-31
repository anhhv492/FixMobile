package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;
import com.fix.mobile.service.SaleDetailService;
import com.fix.mobile.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController @RequestMapping("/admin/rest/sale")
public class SaleRestController {

    @Autowired
    SaleService saleSV;

    @Autowired
    SaleDetailService saleDetailSV;

    @RequestMapping("/demo")
    public Sale addSale(@RequestBody Sale sale){

        System.out.println("truoc");
        return saleSV.add(sale);
    }

    @RequestMapping("/demo1")
    public Sale addDetailSale(@RequestBody Sale sale){

        System.out.println(saleSV.getIDaddSaleDetail());
        return sale;
    }

    @RequestMapping("/demo3")
    public void addDetailSale1(@RequestBody ArrayList<Integer> listID){
        System.out.println("sạu");
        for (int i=0;i<listID.size();i++){
            saleDetailSV.createSaleDetail(listID.get(i));
        }
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
