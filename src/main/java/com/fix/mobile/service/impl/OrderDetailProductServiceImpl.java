package com.fix.mobile.service.impl;

import com.fix.mobile.service.OrderDetailProductService;
import com.japan.shop.repository.OrderDetailProductRepository;
import com.japan.shop.entity.OrderDetailProduct;
import com.japan.shop.service.OrderDetailProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailProductServiceImpl implements OrderDetailProductService {
    private final OrderDetailProductRepository repository;

    public OrderDetailProductServiceImpl(OrderDetailProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderDetailProduct save(OrderDetailProduct entity) {
        return repository.save(entity);
    }

    @Override
    public List<OrderDetailProduct> save(List<OrderDetailProduct> entities) {
        return (List<OrderDetailProduct>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<OrderDetailProduct> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<OrderDetailProduct> findAll() {
        return (List<OrderDetailProduct>) repository.findAll();
    }

    @Override
    public Page<OrderDetailProduct> findAll(Pageable pageable) {
        Page<OrderDetailProduct> entityPage = repository.findAll(pageable);
        List<OrderDetailProduct> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public OrderDetailProduct update(OrderDetailProduct entity, Integer id) {
        Optional<OrderDetailProduct> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}