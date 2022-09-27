package com.japan.shop.repository;

import com.japan.shop.entity.Color;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends PagingAndSortingRepository<Color, Integer> {
}