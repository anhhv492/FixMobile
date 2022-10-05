package com.fix.mobile.repository;

import com.fix.mobile.entity.ProductReturn;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReturnRepository extends PagingAndSortingRepository<ProductReturn, Integer> {
}