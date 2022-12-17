package com.fix.mobile.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.dto.account.AccountRequestDTO;
import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.dto.account.UpdatePasswordDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.RoleService;
import com.fix.mobile.service.SaleDetailService;
import com.fix.mobile.service.SaleService;
import com.fix.mobile.utils.UserName;
import org.hibernate.StaleStateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value= "/rest/user")
@CrossOrigin("*")
@Component
public class UserRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    @Autowired
    SaleService saleSV;

    @Autowired
    ServletContext application;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SaleDetailService saleDetailSV;

    Account account = null;

    public UserRestController(AccountService accountService, RoleService roleService, SaleService saleSV, ServletContext application, ModelMapper modelMapper, SaleDetailService saleDetailSV) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.saleSV = saleSV;
        this.application = application;
        this.modelMapper = modelMapper;
        this.saleDetailSV = saleDetailSV;
    }

    @GetMapping("/getAccountActive")
    public AccountDTO getAccountActive() {
        Account account = accountService.findByUsername(UserName.getUserName());
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }

    @PostMapping("/setaddressdefault")
    public void setaddressdefault(@RequestBody Integer id){
        accountService.setAddressDefault(id);
    }

    @GetMapping("/getAddress")
    public AddressDTO getAddress(){
        return accountService.getAddress();
    }

    @PostMapping("/updateAccountActive")
    public AccountDTO updateAccountActive(@RequestBody AccountDTO accountDTO){
        return accountService.updateAccountActive(accountDTO);
    }

    @PostMapping(value = "/updateImage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AccountResponDTO updateImage(@ModelAttribute AccountRequestDTO accountRequestDTO){
        return accountService.updateImage(accountRequestDTO);
    }

    @PostMapping("/updatePassword")
    public Boolean updatePassword (@RequestBody UpdatePasswordDTO updatePasswordDTO){

        return accountService.updatePassword(updatePasswordDTO);
    }

    @RequestMapping("/sale/getvoucher")
    public List<Sale> getVoucher(@RequestParam(name="money") BigDecimal money, @RequestBody JsonNode carts
    ) {
        if(null==money||"undefined".equals(money)){
            throw new StaleStateException("Đơn hàng của bạn phải > 0 để sử dụng giảm giá");
        }else {
            List<Integer> listIDAccessory = new ArrayList<>();
            List<Integer> listIDProduct = new ArrayList<>();
            String userName;
            account = accountService.findByUsername(UserName.getUserName());
            if (null == account) {
                userName = null;
            } else {
                userName = account.getUsername();
            }
            if (0 == carts.size() || null == carts || "undefined".equals(carts)) {
                listIDAccessory = null;
                listIDProduct = null;
            } else {
                for (int i = 0; i < carts.size(); i++) {
                    if (carts.get(i).get("qty").asInt() <= 0) {
                        return null;
                    } else {
                        if (carts.get(i).get("idAccessory") != null) {
                            listIDAccessory.add(carts.get(i).get("idAccessory").asInt());
                        } else if (carts.get(i).get("idProduct") != null) {
                            listIDProduct.add(carts.get(i).get("idProduct").asInt());
                        }
                    }
                }
            }
            if (0 == listIDAccessory.size()) {
                listIDAccessory = null;
            }
            if (0 == listIDProduct.size()) {
                listIDProduct = null;
            }
            return saleSV.getSaleByVoucher(userName, new BigDecimal(String.valueOf(money)), listIDProduct, listIDAccessory);
        }
    }

    @RequestMapping("/sale/getsale/{id}")
    public Sale finByid(@PathVariable("id") Integer id){
        return saleSV.findByid(id);
    }

    @RequestMapping("/sale/getsaledetail/{id}")
    public List<SaleDetail> finByidsaledetail(@PathVariable("id") Integer id){
        Sale sale = saleSV.findByid(id);
        return saleDetailSV.findByid(sale);
    }

    @RequestMapping("/sale/getbigsale")
    public Sale getBigSale( @RequestParam(name="money") String money,
                            @RequestParam(name="idPrd") Integer idPrd,
                            @RequestParam(name="idAcsr") Integer idAcsr){
        BigDecimal moneySale;
        String userName;
        account = accountService.findByUsername(UserName.getUserName());
        if(null==account){
            userName=null;
        }else{
            userName = account.getUsername();
        }
        if( 0 == money.length() || "undefined".equals(money) ||"".equals(money)){
            moneySale=null;
        }else{
            moneySale = new BigDecimal(Double.valueOf(money));
        }
        if( 0==idPrd ){
            idPrd=null;
        }
        if( 0 == idAcsr){
            idAcsr=null;
        }
//        System.out.println(saleSV.getBigSale(userName,moneySale,idPrdSale,idAcsrSale));
        return saleSV.getBigSale(userName,moneySale,idPrd,idAcsr);
    }
}
