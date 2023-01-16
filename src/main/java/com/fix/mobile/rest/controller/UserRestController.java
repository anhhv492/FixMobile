package com.fix.mobile.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.dto.RoleDTO;
import com.fix.mobile.dto.account.AccountRequestDTO;
import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.dto.account.UpdatePasswordDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.service.*;
import com.fix.mobile.utils.UserName;
import org.hibernate.StaleStateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(value= "/rest/user")
@CrossOrigin("*")
@Component
public class UserRestController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    SaleService saleSV;

    @Autowired
    ProductService productSV;

    @Autowired
    ServletContext application;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SaleDetailService saleDetailSV;

    @Autowired
    private OrderDetailService orderDetailService;
    Account account = null;

    public UserRestController(AccountService accountService, SaleService saleSV, ServletContext application, ModelMapper modelMapper, SaleDetailService saleDetailSV) {
        this.accountService = accountService;

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

    @GetMapping("/getRole")
    public Role getRole(){
        return accountService.getRoleByUserName(UserName.getUserName());
    }



//    @GetMapping("/order")
//    public List<Order> getAllByAccount(){
//        Account account = accountService.findByUsername(UserName.getUserName());
//        List<Order> orders = orderService.findAllByAccount(account);
//        Comparator comparator = new Comparator<Order>() {
//            @Override
//            public int compare(Order o1, Order o2) {
//                return o2.getIdOrder().compareTo(o1.getIdOrder());
//            }
//        };
//        Collections.sort(orders,comparator);
//        return orders;
//    }

//    @PostMapping("/order/change")
//    public Order orderChange(@RequestBody Order order){
//        Order orderOld = orderService.findById(order.getIdOrder()).get();
//        if(orderOld.getStatus()<2){
//            orderOld.setStatus(4);
//            orderOld.setNote(order.getNote());
//            orderService.update(orderOld,orderOld.getIdOrder());
//            return order;
//        }
//        return null;
//    }

    @PostMapping("/sale/addsaleapply")
    public void getSaleOrder(
            @RequestParam(name="idSale") Integer idSale
    ){
        String userName;
        account = accountService.findByUsername(UserName.getUserName());
        if(null==account){
            userName=null;
        }else{
            userName = account.getUsername();
        }
        if(idSale!=0){
            Sale updatequantity= saleSV.findByid(idSale);
            if(updatequantity.getQuantity()==0){
                throw new StaleStateException("Mã giảm giảm giá đã hết lượt sử dụng bạn hãy chọn giá khác");
            }
            updatequantity.setQuantity(updatequantity.getQuantity()-1);
            saleSV.updateQuantity(updatequantity);
            saleSV.addApply_Sale(idSale,userName);
        }
    }

    @RequestMapping("/saleapply/{id}")
    public Sale findSaleApply(@PathVariable("id") Integer id){
        return saleSV.findByid(saleSV.findSaleApply(id));
    }


}
