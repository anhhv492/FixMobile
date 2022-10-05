package com.fix.mobile.repository;

import com.fix.mobile.entity.District;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends PagingAndSortingRepository<District, Integer> {
}