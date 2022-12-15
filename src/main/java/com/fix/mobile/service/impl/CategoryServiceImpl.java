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
        if (repository.getCategoryByTypeAndName(entity.getType(), entity.getName()).orElse(null) != null){
            return null;
        }else {
            return repository.save(entity);
        }

    }

    @Override
    public List<Category> save(List<Category> entities) {
        return (List<Category>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        Category category = repository.findById(id).orElse(null);
        assert category != null;
        category.setStatus(0);
        repository.save(category);
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
        Category category = repository.findById(id).orElse(null);
        assert category != null;
        category.setName(entity.getName());
        category.setType(entity.getType());
        category.setStatus(entity.getStatus());
        return repository.save(category);
    }

    public List<Category> findByType() {
        return repository.findByType(1);
    }


    public Optional<Category> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Category> findByTypeProduct() {
        return null;
    }

    public List<Category> findByTypeSP() {
        return repository.findByType(0);
    }

    @Override
    public Page<Category> page(String name, Integer type, Integer status, Pageable pageable) {
        if (name == "" && status == -1 && type == -1){
            return repository.findByStatus(1, pageable);
        }
        else if (name == null || name == "") {
            if (type == -1) {
                return repository.findByStatus(status, pageable);
            } else if (status == -1) {
                return repository.findByType(type, pageable);
            } else if (type != -1 && status != -1){
                return repository.findByTypeAndStatus(type, status, pageable);
            }else {
                return repository.findByStatus(1, pageable);
            }
        }else {
            if (status == -1 && type== -1){
                return repository.findByNameContaining(name, pageable);
            } else if (status == -1) {
                return repository.findByNameContainingAndType(name, type, pageable);
            } else if (type == -1) {
                return repository.findByNameContainingAndStatus(name, status, pageable);
            }else {
                return repository.findByNameContainingAndTypeAndStatus(name, type, status, pageable);
            }
        }

    }

    @Override
    public void deleteByListId(List<Integer> idList) {
        for (int i = 0; i < idList.size(); i++) {
            Category category = repository.findById(idList.get(i)).orElse(null);
            assert category != null;
            category.setStatus(0);
            repository.save(category);
        }
    }

    @Override
    public List<Category> findAllBybStatus() {
        return repository.findAllByStatus(1);
    }

}