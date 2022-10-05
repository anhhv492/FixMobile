package com.fix.mobile.repository;

import com.fix.mobile.entity.Capacity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacityRepository extends PagingAndSortingRepository<Capacity, Integer> {
}