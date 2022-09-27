package com.example.japanshop.repository;

import com.example.japanshop.entity.Capacity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacityRepository extends PagingAndSortingRepository<Capacity, Integer> {
}