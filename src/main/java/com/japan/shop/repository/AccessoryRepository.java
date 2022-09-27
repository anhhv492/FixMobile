package com.japan.shop.repository;

import com.japan.shop.entity.Accessory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends PagingAndSortingRepository<Accessory, Integer> {
}