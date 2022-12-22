package com.fix.mobile.service.impl;

import com.fix.mobile.service.CapacityService;
import com.fix.mobile.repository.CapacityRepository;
import com.fix.mobile.entity.Capacity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CapacityServiceImpl implements CapacityService {
    private final CapacityRepository repository;

    public CapacityServiceImpl(CapacityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Capacity save(Capacity entity) {
        return repository.save(entity);
    }

    @Override
    public List<Capacity> save(List<Capacity> entities) {
        return (List<Capacity>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Capacity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Capacity> findAll() {
        return (List<Capacity>) repository.findAll();
    }

    @Override
    public Page<Capacity> findAll(Pageable pageable) {
        Page<Capacity> entityPage = repository.findAll(pageable);
        List<Capacity> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Capacity update(Capacity entity, Integer id) {
        Optional<Capacity> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public Optional<Capacity> findByName(String name) {
        return repository.findByName(name);
    }
}