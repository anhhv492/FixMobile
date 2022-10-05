package com.fix.mobile.repository;

import com.fix.mobile.entity.Ram;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepository extends PagingAndSortingRepository<Ram, Integer> {
}