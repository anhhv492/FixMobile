package com.fix.mobile.service;


import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.OrderDetail;

import java.util.List;

public interface OrderService extends GenericService<Order, Integer> {
    List<Order> findAllByAccount(Account account);
}