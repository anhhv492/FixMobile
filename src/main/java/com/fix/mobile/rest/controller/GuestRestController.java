package com.fix.mobile.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.entity.*;
import com.fix.mobile.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/guest")
public class GuestRestController {
    Logger logger = Logger.getLogger(GuestRestController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    Order order = null;
    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping("/category/getAll")
    public List<Category> getAll(){
        return categoryService.findAll();
    }
    //find category by accessory
    @GetMapping("/cate")
    public List<Category> findByCate(){
        return categoryService.findByType();
    }
    @GetMapping(value="/accessory/{id}")
    public Accessory findById(@PathVariable("id") Integer id){
        Optional<Accessory> accessory = accessoryService.findById(id);
        if(accessory.isPresent()){
            return accessory.get();
        }
        return null;
    }
    //find accessory by category
    @GetMapping("/accessory/cate-access/{id}")
    public List<Accessory> findByCateAccessId(@PathVariable("id") Integer id){
        Optional<Category> cate = categoryService.findById(id);
        if(cate.isEmpty()){
            return null;
        }
        List<Accessory> accessories = accessoryService.findByCategoryAndStatus(cate);
        return accessories;
    }
    @GetMapping("/product/cate-product/{id}")
    public List<Product> findByCateProductId(@PathVariable("id") Integer id){
        System.out.println("get product");
        Optional<Category> cate = categoryService.findById(id);
        if(cate.isEmpty()){
            return null;
        }
        List<Product> products = productService.findByCategoryAndStatus(cate);
        for (Product product : products) {
            System.out.println(product.getName());
        }
        return products;
    }
    @PostMapping("/order/add")
    public Order order(@RequestBody Order order, Principal principal){
        Account account = accountService.findByUsername(principal.getName());
        if(order.getAddress()==null||account==null){
            return null;
        }
        this.order = order;
        order.setAccount(account);
        orderService.save(order);
        logger.info("-- Order: "+order.getIdOrder());
        return order;
    }
    @PostMapping("/order-detail/add")
    public JsonNode cartItems(@RequestBody JsonNode productList,Principal principal){
        System.out.println(order.getIdOrder()+"id order");
        OrderDetail orderDetail =null;
        BigDecimal price = new BigDecimal(0);
        for (int i=0;i<productList.size();i++){
            if(productList.get(i).get("qty").asInt()<=0){
                return null;
            }else{
                orderDetail = new OrderDetail();
                if(productList.get(i).get("idAccessory").asInt()>-1){
                    Optional<Accessory> accessory = accessoryService.findById(productList.get(i).get("idAccessory").asInt());
                    if(accessory.isPresent()){
                        orderDetail.setAccessory(accessory.get());
                        orderDetail.setOrder(order);
                        orderDetail.setQuantity(productList.get(i).get("qty").asInt());
                        orderDetail.setPrice(accessory.get().getPrice());
                        orderDetailService.save(orderDetail);
                        accessory.get().setQuantity(accessory.get().getQuantity()-productList.get(i).get("qty").asInt());
                        accessoryService.update(accessory.get(),accessory.get().getIdAccessory());
                    }
                } else if (productList.get(i).get("idProduct").asInt()>-1){
                    Optional<Product> product = productService.findById(productList.get(i).get("idProduct").asInt());
                    if(product.isPresent()){
                        orderDetail.setProduct(product.get());
                        orderDetail.setOrder(order);
                        orderDetail.setQuantity(productList.get(i).get("qty").asInt());
                        orderDetail.setPrice(product.get().getPrice());
                        orderDetailService.save(orderDetail);
                        price =new BigDecimal(product.get().getPrice().doubleValue());
                        orderDetail.setPrice(price);
                        orderDetail.setAccessory(null);
                        orderDetailService.save(orderDetail);
                    }
                }
            }
        }
        logger.info("-- OrderDetail success: "+principal.getName());
        return productList;
    }
}
