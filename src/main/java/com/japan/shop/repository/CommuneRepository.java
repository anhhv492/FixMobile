package com.japan.shop.repository;

import com.japan.shop.entity.Commune;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuneRepository extends PagingAndSortingRepository<Commune, Integer> {
}