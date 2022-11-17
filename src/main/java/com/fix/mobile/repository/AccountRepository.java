package com.fix.mobile.repository;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Account;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
	@Query("SELECT a FROM Account a WHERE a.username LIKE :username AND a.status = 1")
	public Account findByName(@Param ("username") String username);

	@Query(value = "select * from accounts where status = 1 and (full_name like %?1% or username like %?1%)",nativeQuery = true)
	Page<Account> findShowSale(String share, Pageable pageable);
}