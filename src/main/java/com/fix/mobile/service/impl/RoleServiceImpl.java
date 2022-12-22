package com.fix.mobile.service.impl;

import com.fix.mobile.service.RoleService;
import com.fix.mobile.repository.RoleRepository;
import com.fix.mobile.entity.Role;
import com.fix.mobile.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role save(Role entity) {
        return repository.save(entity);
    }

    @Override
    public List<Role> save(List<Role> entities) {
        return (List<Role>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        Page<Role> entityPage = repository.findAll(pageable);
        List<Role> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Role update(Role entity, Integer id) {
        Optional<Role> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}