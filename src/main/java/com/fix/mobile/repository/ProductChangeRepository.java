package com.fix.mobile.repository;

import com.fix.mobile.entity.ProductChange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductChangeRepository extends PagingAndSortingRepository<ProductChange, Integer> {
	@Query(value = "select * from product_change left join change_detail on product_change.id_change = change_detail.id_change",nativeQuery = true)
	public List<ProductChange> findProductChange();
}