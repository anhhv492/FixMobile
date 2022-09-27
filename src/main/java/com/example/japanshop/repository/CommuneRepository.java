package com.example.japanshop.repository;

import com.example.japanshop.entity.Commune;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuneRepository extends PagingAndSortingRepository<Commune, Integer> {
}