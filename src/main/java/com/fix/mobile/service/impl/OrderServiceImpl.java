package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.service.OrderService;
import com.fix.mobile.repository.OrderRepository;
import com.fix.mobile.entity.Order;
import com.fix.mobile.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order entity) {
        return repository.save(entity);
    }

    @Override
    public List<Order> save(List<Order> entities) {
        return (List<Order>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) repository.findAll();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        Page<Order> entityPage = repository.findAll(pageable);
        List<Order> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Order update(Order entity, Integer id) {
        Optional<Order> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public List<Order> findAllByAccount(Account account) {
        return repository.findAllByAccount(account);
    }

}