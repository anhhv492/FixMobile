package com.fix.mobile.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.dto.RoleDTO;
import com.fix.mobile.dto.account.AccountRequestDTO;
import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.dto.account.UpdatePasswordDTO;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Address;
import com.fix.mobile.entity.Role;
import com.fix.mobile.help.HashUtil;

import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.repository.AddressRepository;
import com.fix.mobile.repository.RoleRepository;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.entity.Account;
import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    @Autowired
    private Cloudinary cloud;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;


    public AccountServiceImpl(AccountRepository repository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountResponDTO save(AccountRequestDTO accountRequestDTO) {
        try {
            Account accountCheck = findByUsername(accountRequestDTO.getUsername());
            if (accountCheck != null && accountCheck.getUsername().equals(accountRequestDTO.getUsername())) {
                return null;
            } else {
                Role role = roleRepository.findById(accountRequestDTO.getRole()).orElse(null);
                Date date = new Date();
                Account account = new Account();
                account.setUsername(accountRequestDTO.getUsername());
                account.setFullName(accountRequestDTO.getFullName());
                account.setPassword(HashUtil.hash(accountRequestDTO.getPassword()));
                account.setEmail(accountRequestDTO.getEmail());
                account.setGender(accountRequestDTO.getGender());
                account.setPhone(accountRequestDTO.getPhone());
                account.setStatus(accountRequestDTO.getStatus());
                account.setRole(role);
                account.setCreateDate(date);
                if (accountRequestDTO.getImage() == null){
                    account.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669087979/kbasp5qdf76f3j02mebr.png");
                }else {
                    Map r = this.cloud.uploader().upload(accountRequestDTO.getImage().getBytes(),
                            ObjectUtils.asMap(
                                    "cloud_name", "dcll6yp9s",
                                    "api_key", "916219768485447",
                                    "api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
                                    "secure", true,
                                    "folders", "c202a2cae1893315d8bccb24fd1e34b816"
                            ));
                    account.setImage(r.get("secure_url").toString());
                }

                Account accountSave = repository.save(account);
                AccountResponDTO accountResponDTO = modelMapper.map(accountSave, AccountResponDTO.class);
                accountResponDTO.setRole(accountSave.getRole().getIdRole());
                return accountResponDTO;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public List<Account> save(List<Account> entities) {
        return (List<Account>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(String id) {
        Account account = repository.findByName(id);
        account.setStatus(0);
        repository.save(account);
    }

    @Override
    public Optional<Account> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<AccountResponDTO> findAll() {
        List<Account> accountList = (List<Account>) repository.findAll();
        List<AccountResponDTO> accountResponDTOList = accountList.stream().map(
                account -> modelMapper.map(account, AccountResponDTO.class)).collect(Collectors.toList());

        return accountResponDTOList;
    }

    @Override
    public Page<AccountResponDTO> findAll(String search, Integer role, Integer status, Pageable pageable) {
        if (search == "" && status == -1 && role == 0){
            Page<Account> accountPage = repository.findAllByStatus(1, pageable);
            Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
            return accountResponDTOPage;
        }
        else if (search == null || search == "") {
            if (status != -1 && role == 0) {
                Page<Account> accountPage = repository.findAllByStatus(status, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            } else if (role != null && role != 0 && status == -1) {
                Page<Account> accountPage = repository.findByRole_IdRole(role, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            } else {
                Page<Account> accountPage = repository.findByRole_IdRoleAndStatus(role, status, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            }
        } else {
            if (status == -1 && role == 0) {
                Page<Account> accountPage = repository.findByUsernameContaining(search, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            } else if (status != -1 && role == 0) {
                Page<Account> accountPage = repository.findByUsernameContainingAndStatus(search, status, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            } else if (role != null && role != 0 && status == -1) {
                Page<Account> accountPage = repository.findByUsernameContainingAndRole_IdRole(search, role, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            } else {
                Page<Account> accountPage = repository.findByUsernameContainingAndRole_IdRoleAndStatus(search, role, status, pageable);
                Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
                return accountResponDTOPage;
            }
        }
    }

    @Override
    public AccountResponDTO update(AccountRequestDTO accountRequestDTO, String username) {
        try {

            Role role = roleRepository.findById(accountRequestDTO.getRole()).orElse(null);
            Account account = repository.findByUsername(username);
            account.setFullName(accountRequestDTO.getFullName());
            account.setGender(accountRequestDTO.getGender());
            account.setEmail(accountRequestDTO.getEmail());
            account.setPhone(accountRequestDTO.getPhone());
            account.setRole(role);
            account.setStatus(accountRequestDTO.getStatus());
            if (accountRequestDTO.getImage() == null){
                account.setImage(account.getImage());
            }else {
                Map r = this.cloud.uploader().upload(accountRequestDTO.getImage().getBytes(),
                        ObjectUtils.asMap(
                                "cloud_name", "dcll6yp9s",
                                "api_key", "916219768485447",
                                "api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
                                "secure", true,
                                "folders", "c202a2cae1893315d8bccb24fd1e34b816"
                        ));
                account.setImage(r.get("secure_url").toString());
            }
            Account accountSave = repository.save(account);
            AccountResponDTO accountResponDTO = modelMapper.map(accountSave, AccountResponDTO.class);
            accountResponDTO.setRole(accountSave.getRole().getIdRole());
            return accountResponDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Account findByName(String username) {
        // TODO Auto-generated method stub
        return repository.findByName(username);
    }

    @Override
    public Account findByUsername(String name) {
        return repository.findByName(name);
    }

    @Override
    public String setAddressDefault(Integer id) {
        Address address = addressRepository.findById(id).orElse(null);
        Account account = repository.findByName(UserName.getUserName());
        account.setAddress_id(address);
        repository.save(account);
        return "OK";
    }

    @Override
    public AddressDTO getAddress() {
        Account account = repository.findByName(UserName.getUserName());
        Address address = addressRepository.findById(account.getAddress_id().getIdAddress()).orElse(null);
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    @Override
    public AccountDTO updateAccountActive(AccountDTO accountDTO) {
        Account accountActive = repository.findByName(UserName.getUserName());
        accountActive.setPhone(accountDTO.getPhone());
        accountActive.setEmail(accountDTO.getEmail());
        accountActive.setFullName(accountDTO.getFullName());
        accountActive.setGender(accountDTO.getGender());
        accountActive.setImage(accountDTO.getImage());
        Account account = repository.save(accountActive);
        AccountDTO accountDTORes = modelMapper.map(account, AccountDTO.class);
        return accountDTORes;
    }

    public Page<Account> getByPage(int pageNumber, int maxRecord, String share) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<Account> page = repository.findShowSale(share, pageable);
        return page;

    }

    @Override
    public AccountResponDTO updateImage(AccountRequestDTO accountRequestDTO) {
        try {
            Account account = repository.findByName(UserName.getUserName());
            Map r = this.cloud.uploader().upload(accountRequestDTO.getImage().getBytes(),
                    ObjectUtils.asMap(
                            "cloud_name", "dcll6yp9s",
                            "api_key", "916219768485447",
                            "api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
                            "secure", true,
                            "folders", "c202a2cae1893315d8bccb24fd1e34b816"
                    ));
            account.setImage(r.get("secure_url").toString());
            Account accountSave = repository.save(account);
            AccountResponDTO accountResponDTO = modelMapper.map(accountSave, AccountResponDTO.class);
            return accountResponDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Account account = repository.findByUsername(UserName.getUserName());
        if (HashUtil.verify(updatePasswordDTO.getOldPassword(), account.getPassword())== false){
            return false;
        }else {
            account.setPassword(HashUtil.hash(updatePasswordDTO.getPassword()));
            repository.save(account);
            return true;
        }

    }

    @Override
    public Role getRoleByUserName(String userName) {
        Account account = repository.findByName(userName);

        return account.getRole();
    }
}