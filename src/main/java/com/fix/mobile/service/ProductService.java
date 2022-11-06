package com.fix.mobile.service;


import com.fix.mobile.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService extends GenericService<Product, Integer> {

	Page<Product> getAll (Pageable page);
	Optional<Product> findByName(String name);

}