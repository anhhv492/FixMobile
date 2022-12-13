package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Order;
import com.fix.mobile.service.OrderDetailService;
import com.fix.mobile.repository.OrderDetailRepository;
import com.fix.mobile.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository repository;

    public OrderDetailServiceImpl(OrderDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderDetail save(OrderDetail entity) {
        return repository.save(entity);
    }

    @Override
    public List<OrderDetail> save(List<OrderDetail> entities) {
        return (List<OrderDetail>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<OrderDetail> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<OrderDetail> findAll() {
        return (List<OrderDetail>) repository.findAll();
    }

    @Override
    public Page<OrderDetail> findAll(Pageable pageable) {
        Page<OrderDetail> entityPage = repository.findAll(pageable);
        List<OrderDetail> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public OrderDetail update(OrderDetail entity, Integer id) {
        Optional<OrderDetail> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public List<OrderDetail> findAllByOrder(Order order) {
        return repository.findAllByOrder(order);
    }


}