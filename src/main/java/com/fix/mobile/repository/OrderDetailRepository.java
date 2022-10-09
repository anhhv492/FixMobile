package com.fix.mobile.repository;

import com.fix.mobile.entity.OrderDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Integer> {
}