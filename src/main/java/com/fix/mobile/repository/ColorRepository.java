package com.fix.mobile.repository;

import com.fix.mobile.entity.Color;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends PagingAndSortingRepository<Color, Integer> {
}