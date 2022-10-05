package com.fix.mobile.service.impl;

import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.service.AccountService;
import com.japan.shop.repository.AccountRepository;
import com.japan.shop.entity.Account;
import com.japan.shop.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
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
    public Account update(Account entity, String id) {
        Optional<Account> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}