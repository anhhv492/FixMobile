package com.example.japanshop.repository;

import com.example.japanshop.entity.InsuranceDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceDetailRepository extends PagingAndSortingRepository<InsuranceDetail, Integer> {
}