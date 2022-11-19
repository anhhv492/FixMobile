package com.fix.mobile.service;

import com.fix.mobile.dto.Account.AccountRequestDTO;
import com.fix.mobile.dto.Account.AccountResponDTO;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountResponDTO save(AccountRequestDTO entity);

    Account save(Account account);

    List<Account> save(List<Account> entities);

    void deleteById(String id);

    Optional<Account> findById(String id);

    List<Account> findAll();



    Page<AccountResponDTO> findAll(Pageable pageable);

    AccountDTO update(AccountDTO accountDTO, String username);

    Account findByName(String username);

    Account findByUsername(String name);

    String setAddressDefault(Integer id);

    AddressDTO getAddress();

    AccountDTO updateAccountActive(AccountDTO accountDTO);
}