package com.fix.mobile.service;


import com.fix.mobile.dto.RoleDTO;
import com.fix.mobile.dto.account.AccountRequestDTO;
import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.dto.account.UpdatePasswordDTO;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Role;
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

    List<AccountResponDTO> findAll();

    Page<AccountResponDTO> findAll(String search,Integer role, Integer status, Pageable pageable);

    AccountResponDTO update(AccountRequestDTO accountDTO, String username);

    Account findByName(String username);

    Account findByUsername(String name);


    String setAddressDefault(Integer id);

    AddressDTO getAddress();

    AccountDTO updateAccountActive(AccountDTO accountDTO);

    Page<Account> getByPage(int pageNumber, int maxRecord, String share);

    AccountResponDTO updateImage(AccountRequestDTO accountRequestDTO);

    Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO);

    Role getRoleByUserName(String userName);
}