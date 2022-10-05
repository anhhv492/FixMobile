package com.fix.mobile.repository;

import com.fix.mobile.entity.Commune;
import com.japan.shop.entity.Commune;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuneRepository extends PagingAndSortingRepository<Commune, Integer> {
}