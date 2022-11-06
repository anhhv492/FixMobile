package com.fix.mobile.service;


import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService extends GenericService<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrder(Order order);
}