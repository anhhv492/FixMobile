package com.fix.mobile.service;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Account;
import org.springframework.data.domain.Page;

public interface AccountService extends GenericService<Account, String> {
	Account findByName(String username);

    Account findByUsername(String name);

    Page<Account> getByPage(int pageNumber, int maxRecord, String share);
}