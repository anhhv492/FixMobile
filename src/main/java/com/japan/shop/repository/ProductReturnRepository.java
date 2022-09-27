package com.japan.shop.repository;

import com.japan.shop.entity.ProductReturn;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReturnRepository extends PagingAndSortingRepository<ProductReturn, Integer> {
}