package com.fix.mobile.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.entity.*;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.service.*;
import com.fix.mobile.utils.UserName;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private ImayProductService imayProductService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SaleRepository saleService;
    
    Order order = null;
    Account account = null;
    @GetMapping("/category/getAll")
    public List<Category> getAll(){
        return categoryService.findAll();
    }
    //find category by accessory
    @GetMapping("/cate")
    public List<Category> findByCate(){
        return categoryService.findByType();
    }
    @GetMapping("/imei/amount/{id}")
    public Integer getAmountImei(@PathVariable("id") Integer id){
        Product product = productService.findById(id).get();
        List<ImayProduct> imeis= imayProductService.findByProductAndStatus(product,1);
        return imeis.size();
    }
    @GetMapping("/accessory/amount/{id}")
    public Integer getAmountAccessory(@PathVariable("id") Integer id){
        Accessory accessory = accessoryService.findById(id).get();
        return accessory.getQuantity();
    }
    //find accessory by id
    @GetMapping(value="/accessory/{id}")
    public Accessory findById(@PathVariable("id") Integer id){
        Optional<Accessory> accessory = accessoryService.findById(id);
        if(accessory.isPresent()){
            return accessory.get();
        }
        return null;
    }

    //find product by id
    @GetMapping(value="/product/{id}")
    public Product findProductById(@PathVariable("id") Integer id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            System.out.println(product.get().getName());
            return product.get();
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
        Optional<Category> cate = categoryService.findById(id);
        if(cate.isEmpty()){
            return null;
        }
        List<Product> products = productService.findByCategoryAndStatus(cate);
        for (int i = 0; i < products.size(); i++) {
            List<ImayProduct> imayProducts = imayProductService.findByProductAndStatus(products.get(i),1);
            if(imayProducts.size() == 0){
                products.remove(i);
            }
        }
        return products;
    }
    @PostMapping("/order/add")
    public Order order(@RequestBody Order order){
        System.out.println("b1: "+account.getUsername());
        account = accountService.findByUsername(UserName.getUserName());
        System.out.println("b2: "+account.getUsername());
        if(order.getAddress()==null||account==null){
            return null;
        }
        this.order = order;
        order.setCreateDate(new Date());
        order.setAccount(account);
        System.out.println("b3: "+account.getUsername());
        orderService.save(order);
        logger.info("-- Order: "+order.getIdOrder());
        return order;
    }
    @PostMapping("/order/detail/add")
    public JsonNode cartItems(@RequestBody JsonNode carts){
        account = accountService.findByUsername(UserName.getUserName());
        OrderDetail orderDetail;
        for (int i=0;i<carts.size();i++){
            if(carts.get(i).get("qty").asInt()<=0){
                return null;
            }else{
                orderDetail = new OrderDetail();
                if(carts.get(i).get("idAccessory")!=null){
                    Optional<Accessory> accessory = accessoryService.findById(carts.get(i).get("idAccessory").asInt());
                    if(accessory.isPresent()){
                        orderDetail.setAccessory(accessory.get());
                        orderDetail.setOrder(order);
                        orderDetail.setQuantity(carts.get(i).get("qty").asInt());
                        orderDetail.setPrice(accessory.get().getPrice());
                        orderDetailService.save(orderDetail);
                        accessory.get().setQuantity(accessory.get().getQuantity()-carts.get(i).get("qty").asInt());
                        accessoryService.update(accessory.get(),accessory.get().getIdAccessory());
                    }
                } else if (carts.get(i).get("idProduct")!=null){
                    Optional<Product> product = productService.findById(carts.get(i).get("idProduct").asInt());
                    List<ImayProduct> imayProducts = imayProductService.findByProductAndStatus(product.get(),1);
                    if(product.isPresent()){
                        orderDetail.setProduct(product.get());
                        orderDetail.setOrder(order);
                        orderDetail.setQuantity(carts.get(i).get("qty").asInt());
                        orderDetail.setPrice(product.get().getPrice());
                        orderDetailService.save(orderDetail);
                        for (int j = 0; j < carts.get(i).get("qty").asInt(); j++) {
                            imayProducts.get(j).setOrderDetail(orderDetail);
                            imayProducts.get(j).setStatus(0);
                            imayProductService.update(imayProducts.get(j),imayProducts.get(j).getIdImay());
                        }
                    }
                }
            }
        }
        logger.info("-- OrderDetail success: "+account.getUsername());
        return carts;
    }
    @GetMapping("/cart/sale")
    public List<Sale> getSaleByAccount(@PathVariable("id") Integer id){
        List<Sale> sales = saleService.findAllByDate();
        return sales;
    }
    @GetMapping(value ="/findByProductCode/{productCode}")
    public Optional<Product> findByProductCode(@PathVariable("productCode") Integer productCode) {
        Optional<Product> product = productService.findById(productCode);

        return Optional.of(product.get());
    }
    @GetMapping(value ="/ss")
    public String ss() {

        account = accountService.findByUsername(UserName.getUserName());
        return account.getUsername();
    }
}
