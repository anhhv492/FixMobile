package com.fix.mobile.repository;

import com.fix.mobile.entity.Account;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
	@Query("SELECT a FROM Account a WHERE a.username LIKE :username AND a.status = 1")
	public Account findByName(@Param ("username") String username);
}