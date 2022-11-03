package com.fix.mobile.repository;

import com.fix.mobile.entity.Capacity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CapacityRepository extends PagingAndSortingRepository<Capacity, Integer> {
	Optional<Capacity> findByName(String name);
}