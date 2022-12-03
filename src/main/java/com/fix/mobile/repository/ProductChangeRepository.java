package com.fix.mobile.repository;

import com.fix.mobile.entity.ProductChange;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductChangeRepository extends PagingAndSortingRepository<ProductChange, Integer> {
}