package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Sale;
import com.fix.mobile.service.SaleDetailService;
import com.fix.mobile.repository.SaleDetailRepository;
import com.fix.mobile.entity.SaleDetail;
import com.fix.mobile.service.SaleDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SaleDetailServiceImpl implements SaleDetailService {
    private final SaleDetailRepository repository;

    public SaleDetailServiceImpl(SaleDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public SaleDetail save(SaleDetail entity) {
        return repository.save(entity);
    }

    @Override
    public List<SaleDetail> save(List<SaleDetail> entities) {
        return (List<SaleDetail>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<SaleDetail> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<SaleDetail> findAll() {
        return (List<SaleDetail>) repository.findAll();
    }

    @Override
    public Page<SaleDetail> findAll(Pageable pageable) {
        Page<SaleDetail> entityPage = repository.findAll(pageable);
        List<SaleDetail> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public SaleDetail update(SaleDetail entity, Integer id) {
        Optional<SaleDetail> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public void createSaleDetail(String idthem, Integer idx) {
        if(idx==1){
             repository.creatSaleDetailProduct(idthem);
        }
        if (idx==3){
             repository.creatSaleDetailusername(idthem);
        }
        if (idx==4){
             repository.creatSaleDetailAccessory(idthem);
        }
    }

    @Override
    public void deleteSaleDetai(Integer id) {
        repository.deleteByIdSale(id);
    }

    @Override
    public List<SaleDetail> findByid(Sale sale) {
        return repository.findBySale(sale);
    }

    @Override
    public void updateSaleDetail(String s, Integer idx, Integer id) {
        if(idx==1){
            repository.updateSaleDetailProduct(id,s);
        }
        if (idx==3){
            repository.updateSaleDetailusername(id,s);
        }
        if (idx==4){
            repository.updateSaleDetailAccessory(id,s);
        }
    }
}