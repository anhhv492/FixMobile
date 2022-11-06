package com.fix.mobile.repository;

import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.OrderDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrder(Order order);
}