package com.fix.mobile.repository;

import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	Optional<Product> findByName(String name);
}