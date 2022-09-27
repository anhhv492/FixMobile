package com.japan.shop.repository;

import com.japan.shop.entity.District;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends PagingAndSortingRepository<District, Integer> {
}