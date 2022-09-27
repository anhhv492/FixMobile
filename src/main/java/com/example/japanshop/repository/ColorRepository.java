package com.example.japanshop.repository;

import com.example.japanshop.entity.Color;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends PagingAndSortingRepository<Color, Integer> {
}