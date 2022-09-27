package com.japan.shop.repository;

import com.japan.shop.entity.InsuranceProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends PagingAndSortingRepository<InsuranceProduct, Integer> {
}