package com.japan.shop.repository;

import com.japan.shop.entity.Imei;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImeiRepository extends PagingAndSortingRepository<Imei, Integer> {
}