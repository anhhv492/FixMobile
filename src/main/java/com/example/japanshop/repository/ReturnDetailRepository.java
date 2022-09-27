package com.example.japanshop.repository;

import com.example.japanshop.entity.ReturnDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnDetailRepository extends PagingAndSortingRepository<ReturnDetail, Integer> {
}