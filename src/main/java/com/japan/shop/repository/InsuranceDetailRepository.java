package com.japan.shop.repository;

import com.japan.shop.entity.InsuranceDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceDetailRepository extends PagingAndSortingRepository<InsuranceDetail, Integer> {
}