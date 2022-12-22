package com.fix.mobile.repository;

import com.fix.mobile.entity.Insurance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends PagingAndSortingRepository<Insurance, Integer> {
}