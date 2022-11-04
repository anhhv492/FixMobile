package com.fix.mobile.service;


import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends GenericService<Product, Integer> {
<<<<<<< Updated upstream
=======

	Page<Product> getAll (Pageable page);
	List<Product> findByCategoryAndStatus(Optional<Category> cate);
>>>>>>> Stashed changes
}