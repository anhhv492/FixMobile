package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Category;
import com.fix.mobile.repository.CategoryRepository;
import com.fix.mobile.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category save(Category entity) {
        return repository.save(entity);
    }

    @Override
    public List<Category> save(List<Category> entities) {
        return (List<Category>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) repository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        Page<Category> entityPage = repository.findAll(pageable);
        List<Category> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Category update(Category entity, Integer id) {
        Optional<Category> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
    public List<Category> findByType() {
        return repository.findByType(true);
    }

    public Optional<Category> findByName(String name) {
        return repository.findByName(name);
    }
}