package com.example.japanshop.repository;

import com.example.japanshop.entity.Accessory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends PagingAndSortingRepository<Accessory, Integer> {
}