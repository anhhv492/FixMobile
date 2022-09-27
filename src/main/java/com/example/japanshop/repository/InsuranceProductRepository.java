package com.example.japanshop.repository;

import com.example.japanshop.entity.InsuranceProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends PagingAndSortingRepository<InsuranceProduct, Integer> {
}