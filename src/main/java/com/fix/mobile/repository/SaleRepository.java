package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
    Optional<Sale> findByVoucher(String voucher);
}