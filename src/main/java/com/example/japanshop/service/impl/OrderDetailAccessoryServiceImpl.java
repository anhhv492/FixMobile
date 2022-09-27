package com.example.japanshop.service.impl;

import com.example.japanshop.repository.OrderDetailAccessoryRepository;
import com.example.japanshop.entity.OrderDetailAccessory;
import com.example.japanshop.service.OrderDetailAccessoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailAccessoryServiceImpl implements OrderDetailAccessoryService {
    private final OrderDetailAccessoryRepository repository;

    public OrderDetailAccessoryServiceImpl(OrderDetailAccessoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderDetailAccessory save(OrderDetailAccessory entity) {
        return repository.save(entity);
    }

    @Override
    public List<OrderDetailAccessory> save(List<OrderDetailAccessory> entities) {
        return (List<OrderDetailAccessory>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<OrderDetailAccessory> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<OrderDetailAccessory> findAll() {
        return (List<OrderDetailAccessory>) repository.findAll();
    }

    @Override
    public Page<OrderDetailAccessory> findAll(Pageable pageable) {
        Page<OrderDetailAccessory> entityPage = repository.findAll(pageable);
        List<OrderDetailAccessory> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public OrderDetailAccessory update(OrderDetailAccessory entity, Integer id) {
        Optional<OrderDetailAccessory> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}