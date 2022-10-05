package com.fix.mobile.repository;

import com.fix.mobile.entity.OrderDetailAccessory;
import com.japan.shop.entity.OrderDetailAccessory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailAccessoryRepository extends PagingAndSortingRepository<OrderDetailAccessory, Integer> {
}