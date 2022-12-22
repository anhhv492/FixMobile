package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.helper.WriteExcel;
import com.fix.mobile.repository.AccessoryRepository;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.repository.OrderRepository;
import com.fix.mobile.service.*;
import com.fix.mobile.utils.UserName;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@CrossOrigin("*")
public class OrderRestController {
    Logger logger = Logger.getLogger(OrderRestController.class);
    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ImayProductService imayProductService;
    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private sendMailService sendMailService;
    @Autowired
    private WriteExcel writeExcel;
    @PutMapping(value="/rest/staff/order")
    public Order update(@RequestBody Order order){
        Account account = accountService.findByUsername(UserName.getUserName());
        Order orderOld = orderService.findById(order.getIdOrder()).get();
//        if((order.getStatus()-orderOld.getStatus())!=1){
//            return null;
//        }
        if(order.getStatus()<orderOld.getStatus()){
            return null;
        }
        if(order.getStatus()==2){
            List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(orderOld);
            for (int i = 0; i < orderDetails.size(); i++) {
                Accessory accessory = orderDetails.get(i).getAccessory();
                if(accessory!=null){
                    accessory.setQuantity(accessory.getQuantity()-orderDetails.get(i).getQuantity());
                    accessoryService.update(accessory,accessory.getIdAccessory());
                }
            }
            orderOld.setStatus(order.getStatus());
            orderService.update(orderOld,orderOld.getIdOrder());
            sendMailService.sendEmailOrder("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",
                    orderOld.getAccount().getEmail(), orderOld.getPersonTake(), orderOld);
            return orderOld;
        }
        if(order.getStatus()==4){
            List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(orderOld);
            for (int i = 0; i < orderDetails.size(); i++) {
                Accessory accessory = orderDetails.get(i).getAccessory();
                if(accessory!=null){
                    accessory.setQuantity(accessory.getQuantity()+orderDetails.get(i).getQuantity());
                    accessoryService.update(accessory,accessory.getIdAccessory());
                }
                List<ImayProduct> imayProducts = imayProductService.findByOrderDetail(orderDetails.get(i));
                if(imayProducts.size()>0){
                    for (int j = 0; j < imayProducts.size(); j++) {
                        imayProducts.get(j).setStatus(1);
                        imayProductService.update(imayProducts.get(j),imayProducts.get(j).getIdImay());
                    }
                }
            }
            orderOld.setStatus(order.getStatus());
            orderService.update(orderOld,orderOld.getIdOrder());
            sendMailService.sendEmailOrder("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",
                    orderOld.getAccount().getEmail(), orderOld.getPersonTake(), orderOld);
            System.out.println("gửi mail thành công");
            return orderOld;
        }
        orderOld.setStatus(order.getStatus());
        if(account.getRole().getName().equals("ADMIN")||account.getRole().getName().equals("STAFF")){
            if(order.getStatus()==3){
                orderOld.setTimeReceive(new Date());
            }
            logger.info("-- Account: "+account.getUsername()+" update order success: "+orderOld.getIdOrder()+" to status: "+order.getStatus());
            orderService.update(orderOld,orderOld.getIdOrder());
            sendMailService.sendEmailOrder("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",
                    orderOld.getAccount().getEmail(), orderOld.getPersonTake(), orderOld);
            System.out.println("gửi mail thành công");
            return orderOld;
        }
        return null;
    }
    @GetMapping(value="/rest/staff/order")
    public List<Order> getAll(){
        List<Order> orders = orderService.findAll();
        Comparator<Order> comparator = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getIdOrder().compareTo(o1.getIdOrder());
            }
        };
        Collections.sort(orders,comparator);
        return orders;
    }

    @GetMapping(value="/rest/staff/order/export-excel")
    public List<Order> exportExcel() throws Exception {
        List<Order> orders = orderService.findAll();
        writeExcel.writeExcel(orders);
        return orders;
    }

    @GetMapping(value="/rest/staff/order/{id}")
    public Order findOrderById(@PathVariable("id") Integer id){
        Order order = orderService.findById(id).get();
        return order;
    }
    @GetMapping(value={"/rest/staff/order/date","/rest/user/order/date"})
    public List<Order> findByDate(@RequestParam("date1") String date1, @RequestParam("date2") String date2){
        System.out.println("--"+date1+" "+date2);
        Date dates1 = new Date(date1);
        Date dates2 = new Date(date2);
        List<Order> orders = orderRepository.findAllByCreateDateBetweenNative(dates1,dates2);
        return orders;
    }
    @GetMapping(value={"/rest/staff/order/status/{status}","/rest/user/order/status/{status}"})
    public List<Order> findByStatus(@PathVariable("status") Integer status){
        List<Order> orders = orderRepository.findAllByStatus(status);
        return orders;
    }
    @GetMapping(value={"/rest/staff/order/price","/rest/user/order/price"})
    public List<Order> findByPrice(@RequestParam("price1") BigDecimal price1, @RequestParam("price2") BigDecimal price2){
        List<Order> orders = orderRepository.findAllByTotalBetween(price1,price2);
        return orders;
    }
    @GetMapping(value={"/rest/staff/order/usernames","/rest/user/order/usernames"})
    public List<AccountResponDTO> findByAllAcc(){
        List<AccountResponDTO> accounts = accountService.findAll();
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(accounts.get(i).getUsername());
        }
        return accounts;
    }
    @GetMapping(value={"/rest/staff/order/accounts/{username}","/rest/user/order/accounts/{username}"})
    public List<Order> findAllByUser(@PathVariable("username") String username){
        List<Order> orders = orderRepository.findAllByAccount(accountRepository.findByUsername(username));
        return orders;
    }
    @GetMapping(value={"/rest/staff/order/name/{name}","/rest/user/order/name/{name}"})
    public List<Order> findAllByName(@PathVariable("name") String name){
        List<Order> orders = orderRepository.findAllByName(name);
        return orders;
    }

    @GetMapping(value="/rest/staff/order/getDetail/{idOrder}")
    public Order findAllDetail(@PathVariable("idOrder") Integer idOrder){
        Order order = orderService.findById(idOrder).get();
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order);
        List<ImayProduct> imayProducts = null;
        for (int i = 0; i < orderDetails.size(); i++) {
            if (orderDetails.get(i).getAccessory()!=null){
                if(orderDetails.get(i).getQuantity()>orderDetails.get(i).getAccessory().getQuantity()){
                    System.out.println("Số lượng phụ kiện không đủ-------");
                    return order;
                }
            }
            if(orderDetails.get(i).getProduct()!=null){
                imayProducts = imayProductService.findByOrderDetail(orderDetails.get(i));
                if(imayProducts.size()>=0){
                    if(imayProducts.size()<orderDetails.get(i).getQuantity()){
                        System.out.println("Số lượng sản phẩm không đủ-------");
                        return order;
                    }
                }
            }
        }
        return null;
    }
    @GetMapping("/rest/user/order")
    public List<Order> getAllByAccount(){
        Account account = accountService.findByUsername(UserName.getUserName());
        List<Order> orders = orderService.findAllByAccount(account);
        Comparator comparator = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getIdOrder().compareTo(o1.getIdOrder());
            }
        };
        Collections.sort(orders,comparator);
        return orders;
    }

    @PostMapping(value = "/rest/user/order/change")
    public Order orderChange(@RequestBody Order order){
        Order orderOld = orderService.findById(order.getIdOrder()).get();
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrder(orderOld);
        if(orderOld.getStatus()<2){
            orderOld.setStatus(4);
            orderOld.setNote(order.getNote());
            orderService.update(orderOld,orderOld.getIdOrder());
            for (int i = 0; i < orderDetails.size(); i++) {
                Accessory accessory = orderDetails.get(i).getAccessory();
                if(accessory!=null){
                    accessory.setQuantity(accessory.getQuantity()+orderDetails.get(i).getQuantity());
                    accessoryService.update(accessory,accessory.getIdAccessory());
                }
                List<ImayProduct> imayProducts = imayProductService.findByOrderDetail(orderDetails.get(i));
                if(imayProducts.size()>=0){
                    for (int j = 0; j < imayProducts.size(); j++) {
                        imayProducts.get(j).setStatus(1);
                        imayProductService.update(imayProducts.get(j),imayProducts.get(j).getIdImay());
                    }
                }
            }
            sendMailService.sendEmailOrder("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",
                    orderOld.getAccount().getEmail(), orderOld.getPersonTake(), orderOld);
            return order;
        }
        return null;
    }
    @GetMapping(value="/cart-accessory/{id}")
    public Accessory findById(@PathVariable("id") Integer id){
        Optional<Accessory> accessory = accessoryService.findById(id);
        if(accessory.isPresent()){
            return accessory.get();
        }
        return null;
    }

}
