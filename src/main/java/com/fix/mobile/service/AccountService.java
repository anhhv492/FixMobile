package com.fix.mobile.service;

import com.fix.mobile.entity.Account;

public interface AccountService extends GenericService<Account, String> {
	Account findByName(String username);
}