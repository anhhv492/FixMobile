package com.japan.shop.repository;

import com.japan.shop.entity.Capacity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacityRepository extends PagingAndSortingRepository<Capacity, Integer> {
}