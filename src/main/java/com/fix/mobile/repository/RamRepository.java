package com.fix.mobile.repository;

import com.fix.mobile.entity.Ram;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RamRepository extends PagingAndSortingRepository<Ram, Integer> {
	Optional<Ram> findByName(String name);
}