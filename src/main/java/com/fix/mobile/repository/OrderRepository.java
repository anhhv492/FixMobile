package com.fix.mobile.repository;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
    //find all order
    List<Order> findAllByAccount(Account account);

    // find All By Create Date Between
    @Query(value = "SELECT * FROM `orders` WHERE create_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Order> findAllByCreateDateBetweenNative(Date date1, Date date2);

    List<Order> findAllByTotalBetween(BigDecimal price1, BigDecimal price2);

    @Query(value = "SELECT * FROM `orders` WHERE person_take like %?1%", nativeQuery = true)
    List<Order> findAllByName(String name);

    List<Order> findAllByStatus(Integer status);

    List<Order> findAllByIdOrder(Integer id);
}