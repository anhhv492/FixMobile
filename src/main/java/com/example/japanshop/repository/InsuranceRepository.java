package com.example.japanshop.repository;

import com.example.japanshop.entity.Insurance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends PagingAndSortingRepository<Insurance, Integer> {
}