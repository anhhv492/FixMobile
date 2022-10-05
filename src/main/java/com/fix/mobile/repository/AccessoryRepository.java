package com.fix.mobile.repository;

import com.fix.mobile.entity.Accessory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends PagingAndSortingRepository<Accessory, Integer> {
}