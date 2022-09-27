package com.japan.shop.repository;

import com.japan.shop.entity.OrderDetailAccessory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailAccessoryRepository extends PagingAndSortingRepository<OrderDetailAccessory, Integer> {
}