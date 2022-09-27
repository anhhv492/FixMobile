package com.example.japanshop.repository;

import com.example.japanshop.entity.Imei;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImeiRepository extends PagingAndSortingRepository<Imei, Integer> {
}