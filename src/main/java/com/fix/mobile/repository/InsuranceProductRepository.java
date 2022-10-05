package com.fix.mobile.repository;

import com.fix.mobile.entity.InsuranceProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends PagingAndSortingRepository<InsuranceProduct, Integer> {
}