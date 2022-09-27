package com.example.japanshop.repository;

import com.example.japanshop.entity.District;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends PagingAndSortingRepository<District, Integer> {
}