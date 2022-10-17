package com.fix.mobile.repository;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessoryRepository extends PagingAndSortingRepository<Accessory, Integer> {
    List<Accessory> findByCategory(Optional<Category> cate);
}