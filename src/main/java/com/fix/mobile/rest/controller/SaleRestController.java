package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;
import com.fix.mobile.service.SaleDetailService;
import com.fix.mobile.service.SaleService;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

@RestController @RequestMapping("/admin/rest/sale")
public class SaleRestController {

    @Autowired
    SaleService saleSV;

    @Autowired
    SaleDetailService saleDetailSV;

    @RequestMapping("/add")
    public Sale addSale(@RequestBody Sale sale){
        return saleSV.add(sale);
    }
    @RequestMapping("/update")
    public Sale updateSale(@RequestBody Sale sale){
        return saleSV.update(sale);
    }
    @RequestMapping("/delete")
    public Sale deleteSale(@RequestBody Sale sale){
        sale.setQuantity(0);
        return saleSV.update(sale);
    }
private void checkList(List list,Integer idx,Integer id){
    if(list.size()==0){
        String mess="";
        if(idx==1){
            mess="sản phẩm";
        }else if(idx==3){
            mess="người dùng";
        }else if(idx==4){
            mess="phụ kiện";
        }
        if(id==null){
            saleSV.delete(saleSV.getLimit1Sale());
        }
        throw new StaleStateException("Bạn phải chọn dữ liệu "+mess+" ở bảng");
    }
}
    @RequestMapping("/adddetail/{idx}")
    public void addDetailSale1(@PathVariable(name="idx") Integer idx,
                               @RequestBody ArrayList<String> listID){
        checkList(listID,idx,null);
        for (int i=0;i<listID.size();i++){
            saleDetailSV.createSaleDetail(listID.get(i),idx);
        }
    }

    @RequestMapping("/updatedetail/{idx}/{id}")
    public void updatedetail(@PathVariable(name="idx") Integer idx,
                             @PathVariable(name="id") Integer id,
                             @RequestBody ArrayList<String> listID){
        checkList(listID,idx,id);
        saleDetailSV.deleteSaleDetai(id);
        for (int i=0;i<listID.size();i++){
            saleDetailSV.updateSaleDetail(listID.get(i),idx,id);
        }
    }
    @RequestMapping("/getall/{page}")
    public Page<Sale> getAll(@PathVariable ("page") Integer page,
                             @RequestParam ("stt") Integer stt,
                             @RequestParam ("share") String share,
                             @RequestParam ("type") String type
                             ){
        return saleSV.getByPage(page,5,stt,null,null);
    }
    @RequestMapping("/getsale/{id}")
    public Sale finByid(@PathVariable("id") Integer id){
        return saleSV.findByid(id);
    }
    @RequestMapping("/getsaledetail/{id}")
    public List<SaleDetail> finByidsaledetail(@PathVariable("id") Integer id){
        Sale sale = saleSV.findByid(id);
        return saleDetailSV.findByid(sale);
    }

    @RequestMapping("/demotb")
    public String login(Authentication auth) {
        return auth.toString();
    }
}
