package com.fix.mobile.repository;

import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.ProductChange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductChangeRepository extends PagingAndSortingRepository<ProductChange, Integer> {
	@Query(value = "select * from product_change left join change_detail on product_change.id_change = change_detail.id_change",nativeQuery = true)
	public List<ProductChange> findProductChange();

	@Query("select pr from ProductChange pr order by  pr.idChange")
	public List<ProductChange> findAllProductChange();

	@Query("select pr from ProductChange pr where  pr.idChange = ?1")
	ProductChange findByStatus(Integer idPrChange);
	@Query("select pr from ProductChange pr where pr.account = ?1")
	public List<ProductChange> findByAccount(String username);

	@Query("select pr from ProductChange pr where pr.status = 2 and pr.idChange = ?1")
	public List<ProductChange> findByStatusSendEmail(Integer idChange);


	ProductChange findAllByOrderDetail(OrderDetail orderDetail);

	ProductChange findByOrderDetail(OrderDetail orderDetail);
}