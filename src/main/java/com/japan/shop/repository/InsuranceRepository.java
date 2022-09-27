package com.japan.shop.repository;

import com.japan.shop.entity.Insurance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends PagingAndSortingRepository<Insurance, Integer> {
}