package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
    Optional<Sale> findByVoucher(String voucher);
    @Query(value = "select id_sale from Sale order by id_sale desc limit 1",nativeQuery = true)
    Integer getIDaddSaleDetail();


}