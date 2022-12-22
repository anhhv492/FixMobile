package com.fix.mobile.repository;

import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.ProductChange;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeDetailRepository extends PagingAndSortingRepository<ChangeDetail, Integer> {
	@Modifying
	@Query(value = "insert into change_detail(id_order_detail,id_change) values(?1," +
			"(select id_change from product_change order by id_change  desc limit 1))",nativeQuery = true)
	void createChangeDetails(Integer id);

	@Query("select prChangeDetail from ChangeDetail  prChangeDetail where prChangeDetail.productChange = ?1")
	public ChangeDetail findPrChangeDetails(ProductChange idPrChange);


}