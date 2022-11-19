package com.fix.mobile.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.dto.Account.AccountRequestDTO;
import com.fix.mobile.dto.Account.AccountResponDTO;
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
            accountResponDTO.setRole(accountSave.getRole().getIdRole());
            return accountResponDTO;
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
        repository.deleteById(id);
    }

    @Override
    public Optional<Account> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList = (List<Account>) repository.findAll();
        List<AccountResponDTO> accountResponDTOList = new ArrayList<>();
        AccountResponDTO accountResponDTO = new AccountResponDTO();
        for (int i = 0; i < accountList.size(); i++) {
            accountResponDTO.setUsername(accountList.get(i).getUsername());
            accountResponDTO.setFullName(accountList.get(i).getFullName());
            accountResponDTO.setPassword(accountList.get(i).getPassword());
            accountResponDTO.setEmail(accountList.get(i).getEmail());
            accountResponDTO.setGender(accountList.get(i).getGender());
            accountResponDTO.setPhone(accountList.get(i).getPhone());
            accountResponDTO.setStatus(accountList.get(i).getStatus());
            accountResponDTO.setRole(accountList.get(i).getRole().getIdRole());
            accountResponDTO.setCreateDate(accountList.get(i).getCreateDate());
            accountResponDTO.setImage(accountList.get(i).getImage());
            accountResponDTOList.add(accountResponDTO);
        }

        return (List<Account>) repository.findAll();
    }

    @Override
    public Page<AccountResponDTO> findAll(Pageable pageable) {
        Page<Account> accountPage = repository.findAll(pageable);
        Page<AccountResponDTO> accountResponDTOPage = accountPage.map(entityPage -> modelMapper.map(entityPage, AccountResponDTO.class));
        return accountResponDTOPage;
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO, String username) {
        Account optional = findByName(username);

        return null;
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
}