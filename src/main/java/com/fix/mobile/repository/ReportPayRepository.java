package com.fix.mobile.repository;

import com.fix.mobile.entity.Category;
import com.fix.mobile.model.ReportPaymentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportPayRepository extends PagingAndSortingRepository<ReportPaymentModel, Integer> {
   @Query(value ="select * from customerorders c where c.fullname = ?1 and c.createDate = ?2 ",nativeQuery = true)
   public List<ReportPaymentModel> findByFullnameAndcreateDate(String fullName , String createDate);
}