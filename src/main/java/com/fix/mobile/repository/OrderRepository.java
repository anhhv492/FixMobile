package com.fix.mobile.repository;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.OrderDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
    //find all order
    List<Order> findAllByAccount(Account account);
}