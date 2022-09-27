package com.example.japanshop.repository;

import com.example.japanshop.entity.OrderDetailProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailProductRepository extends PagingAndSortingRepository<OrderDetailProduct, Integer> {
}