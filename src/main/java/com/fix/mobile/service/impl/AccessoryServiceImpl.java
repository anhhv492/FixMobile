package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Sale;
import com.fix.mobile.repository.AccessoryRepository;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.entity.Accessory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
        if (entity.getQuantity()<=0){
            entity.setStatus(false);
        }else{
            entity.setStatus(true);
        }
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
            if (optional.get().getQuantity()<=0){
                entity.setStatus(false);
            }else{
                entity.setStatus(true);
            }
            return save(entity);
        }
        return null;
    }

    @Override
    public List<Accessory> findByCategoryAndStatus(Optional<Category> cate) {
        return repository.findByCategoryAndStatus(cate,true);
    }

    @Override
    public Page<Accessory> getByPage(int pageNumber, int maxRecord, String name) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<Accessory> page = repository.findShowSale(name,pageable);
        return page;
    }

}