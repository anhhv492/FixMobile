package com.fix.mobile.repository;

import com.fix.mobile.entity.InsuranceDetail;
import com.fix.mobile.entity.InsuranceDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceDetailRepository extends PagingAndSortingRepository<InsuranceDetail, Integer> {
}