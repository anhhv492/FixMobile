package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
    Optional<Sale> findByVoucher(String voucher);

    @Query(value = "select max(s.money_sale) , max(s.percent_sale)  from sale s LEFT join sale_detail sd ON s.id_sale =sd.id_sale where type_sale = 0 or type_sale =1 and discount_method = 1",nativeQuery = true)
    List maxSale();
}