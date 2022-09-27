package com.example.japanshop.repository;

import com.example.japanshop.entity.Ram;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepository extends PagingAndSortingRepository<Ram, Integer> {
}