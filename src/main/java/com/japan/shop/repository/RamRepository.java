package com.japan.shop.repository;

import com.japan.shop.entity.Ram;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepository extends PagingAndSortingRepository<Ram, Integer> {
}