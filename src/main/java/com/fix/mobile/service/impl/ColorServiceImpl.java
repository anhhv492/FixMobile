package com.fix.mobile.service.impl;

import com.fix.mobile.service.ColorService;
import com.fix.mobile.repository.ColorRepository;
import com.fix.mobile.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ColorServiceImpl implements ColorService {
    private final ColorRepository repository;

    public ColorServiceImpl(ColorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Color save(Color entity) {
        return repository.save(entity);
    }

    @Override
    public List<Color> save(List<Color> entities) {
        return (List<Color>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Color> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Color> findAll() {
        return (List<Color>) repository.findAll();
    }

    @Override
    public Page<Color> findAll(Pageable pageable) {
        Page<Color> entityPage = repository.findAll(pageable);
        List<Color> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Color update(Color entity, Integer id) {
        Optional<Color> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public Optional<Color> findByName(String name) {
        return repository.findByName(name);
    }
}