package com.example.japanshop.repository;

import com.example.japanshop.entity.ChangeDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeDetailRepository extends PagingAndSortingRepository<ChangeDetail, Integer> {
}