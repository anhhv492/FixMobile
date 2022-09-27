package com.japan.shop.repository;

import com.japan.shop.entity.ProductChange;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductChangeRepository extends PagingAndSortingRepository<ProductChange, Integer> {
}