package com.fix.mobile.repository;

import com.fix.mobile.entity.OrderDetailProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailProductRepository extends PagingAndSortingRepository<OrderDetailProduct, Integer> {
}