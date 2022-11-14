package com.fix.mobile.service;

import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Account;

public interface AccountService extends GenericService<Account, String> {
	Account findByName(String username);

    Account findByUsername(String name);

    String setAddressDefault(Integer id);

    AddressDTO getAddress();
}