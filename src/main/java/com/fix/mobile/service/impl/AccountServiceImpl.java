package com.fix.mobile.service.impl;

import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Address;
import com.fix.mobile.help.HashUtil;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.repository.AddressRepository;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.entity.Account;
import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;




    public AccountServiceImpl(AccountRepository repository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Account save(Account entity) {
        return repository.save(entity);
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
        return (List<Account>) repository.findAll();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        Page<Account> entityPage = repository.findAll(pageable);
        List<Account> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO, String username) {
        Account optional = findByName(username) ;

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