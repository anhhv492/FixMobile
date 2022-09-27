package com.example.japanshop.service.impl;

import com.example.japanshop.repository.AccessoryRepository;
import com.example.japanshop.entity.Accessory;
import com.example.japanshop.service.AccessoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccessoryServiceImpl implements AccessoryService {
    private final AccessoryRepository repository;

    public AccessoryServiceImpl(AccessoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Accessory save(Accessory entity) {
        return repository.save(entity);
    }

    @Override
    public List<Accessory> save(List<Accessory> entities) {
        return (List<Accessory>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Accessory> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Accessory> findAll() {
        return (List<Accessory>) repository.findAll();
    }

    @Override
    public Page<Accessory> findAll(Pageable pageable) {
        Page<Accessory> entityPage = repository.findAll(pageable);
        List<Accessory> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Accessory update(Accessory entity, Integer id) {
        Optional<Accessory> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}