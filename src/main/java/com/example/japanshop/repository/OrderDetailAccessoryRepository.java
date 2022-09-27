package com.example.japanshop.repository;

import com.example.japanshop.entity.OrderDetailAccessory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailAccessoryRepository extends PagingAndSortingRepository<OrderDetailAccessory, Integer> {
}