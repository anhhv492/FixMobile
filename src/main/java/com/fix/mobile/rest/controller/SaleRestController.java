package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.SaleDetailService;
import com.fix.mobile.service.SaleService;
import com.fix.mobile.utils.UserName;
import org.hibernate.StaleStateException;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController @RequestMapping("/admin/rest/sale")
public class SaleRestController {

    @Autowired
    SaleService saleSV;

    @Autowired
    SaleDetailService saleDetailSV;

    @Autowired
    AccountService accountService;

    Account account = null;
    @PostMapping("/add")
    public Sale addSale(@RequestBody Sale sale){
        account = accountService.findByUsername(UserName.getUserName());
        sale.setUserCreate(account.getUsername());
        return saleSV.add(sale);
    }
    @RequestMapping("/update")
    public Sale updateSale(@RequestBody Sale sale){
        account = accountService.findByUsername(UserName.getUserName());
        sale.setUserUpdate(account.getUsername());
        return saleSV.update(sale);
    }
    @RequestMapping("/delete")
    public Sale deleteSale(@RequestBody Sale sale){
        account = accountService.findByUsername(UserName.getUserName());
        sale.setUserUpdate(account.getUsername());
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
    @RequestMapping("/getbigsale")
    public Sale getBigSale( @RequestParam(name="money") String money,
                            @RequestParam(name="idPrd") String idPrd,
                            @RequestParam(name="idAcsr") String idAcsr){
        BigDecimal moneySale;
        Integer idPrdSale;
        Integer idAcsrSale;
        String userName;
        account = accountService.findByUsername(UserName.getUserName());
        if(null==account){
            userName=null;
        }else{
            userName = account.getUsername();
        }
        if( 0 == money.length() || "undefined".equals(money)){
            moneySale=null;
        }else{
            moneySale = new BigDecimal(Double.valueOf(money));
        }
        if( 0 == idPrd.length() || "undefined".equals(idPrd)){
            idPrdSale=null;
        }else{
            idPrdSale=Integer.parseInt(idPrd);
        }
        if( 0 == idAcsr.length() || "undefined".equals(idAcsr)){
            idAcsrSale=null;
        }else{
            idAcsrSale = Integer.parseInt(idAcsr);
        }
//        System.out.println(saleSV.getBigSale(userName,moneySale,idPrdSale,idAcsrSale));
        return saleSV.getBigSale(userName,moneySale,idPrdSale,idAcsrSale);
    }

    @RequestMapping("/demotb")
    public String login(Authentication auth) {
        return auth.toString();
    }
}
