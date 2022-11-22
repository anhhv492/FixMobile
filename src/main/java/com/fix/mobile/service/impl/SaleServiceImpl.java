package com.fix.mobile.service.impl;

import org.hibernate.StaleStateException;
import com.fix.mobile.repository.SaleDetailRepository;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.service.SaleService;
import com.fix.mobile.entity.Sale;
//import org.springframework.security.core.Authentication; cuongnd edit
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    SaleRepository dao;

    @Autowired
    SaleDetailRepository dao1;

    @Override
    public Sale add(Sale sale) {
//        vaildate_NULL(sale);
        sale.setIdSale(null);
        sale.setValueMin(null);
        sale.setCreateTime(new Date());
        sale.setUpdateTime(null);
        sale.setUserCreate(1); //cuongnd edit
        return dao.save(sale);
    }

    @Override
    public Sale update(Sale sale) {
        sale.setUserpdate(1);
        sale.setUpdateTime(new Date());
        return dao.save(sale);
    }

    @Override
    public Sale delete(Integer id) {
        return null;
    }

    @Override
    public List<Sale> getall(Integer status) {
        return null;
    }

    @Override
    public Sale findByid(Integer id) {
        return dao.findByIdSale(id);
    }

    public void validate_Date(Sale sale) {
        SimpleDateFormat viewDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        Date day = new Date();
        if (sale.getIdSale() == null) {
            if (sale.getCreateStart().before(day)) {
                throw new StaleStateException("Thời gian bắt đầu không thể trước " + viewDate.format(day));
            }
            if (sale.getCreateEnd().before(sale.getCreateStart())) {
                throw new StaleStateException("Thời gian kết thúc không thể trước Thời gian bắt đầu");
            }
        } else {
            Sale saleud = dao.findById(sale.getIdSale()).get();
            if(saleud.getCreateStart().before(day)) {
                if (!saleud.getCreateStart().equals(sale.getCreateStart())) {
                    throw new StaleStateException("Không thể sửa Thời gian bắt đầu");
                }
                if (sale.getCreateEnd().before(day)) {
                    throw new StaleStateException("Thời gian kết thúc không thể trước " + viewDate.format(day));
                }
            }
            if (sale.getCreateStart().before(day)) {
                throw new StaleStateException("Thời gian bắt đầu không thể trước " + viewDate.format(day));
            }
            if (sale.getCreateEnd().before(sale.getCreateStart())) {
                throw new StaleStateException("Thời gian kết thúc không thể trước Thời gian bắt đầu");
            }
        }
    }

    public void vaildate_NULL(Sale sale){
        if(sale.getTypeSale()==null){
            throw new StaleStateException("Bạn phải chọn loại giảm giá");
        }
        if(sale.getName()==null){
            throw new StaleStateException("Bạn không thể bỏ trống Tên chương trình");
        }
        if(sale.getDiscountMethod() == null){
            throw new StaleStateException("Bạn phải chọn Phương thức giảm giá");
        }
        if(sale.getDiscountMethod() == 0){
            if(sale.getVoucher() == null ){
                throw new StaleStateException("Bạn không thể bỏ trống Mã giảm giá");
            }
        }
        if(sale.getDiscountType() == null){
            throw new StaleStateException("Bạn phải chọn Kiểu giảm giá");
        }
        if(sale.getDiscountType() == 0){
            if(sale.getMoneySale() == null){
                throw new StaleStateException("Bạn không thể bỏ trống Mức giảm giá");
            }
        }
        if(sale.getDiscountType() == 1){
            if(sale.getPercentSale() == null){
                throw new StaleStateException("Bạn không thể bỏ trống Mức giảm giá");
            }
        }
        if(sale.getQuantity() == null){
            throw new StaleStateException("Bạn không thể bỏ trống Số lượng");
        }
        if(sale.getCreateStart() == null){
            throw new StaleStateException("Bạn không thể bỏ trống Thời gian bắt đầu");
        }
        if(sale.getCreateEnd() == null){
            throw new StaleStateException("Bạn không thể bỏ trống Thời gian kết thúc");
        }
        if(sale.getTypeSale() == 2 ){
            if(sale.getValueMin() == null ){
                throw new StaleStateException("Bạn không thể bỏ trống Giá trị đơn hàng tối thiểu");
            }
        }
    }

    public void validate_NumberANDLength(Sale sale){
        if(sale.getName().length() > 250 ){
            throw new StaleStateException("Tên chương trình được nhập tối đa 250 kí tự");
        }
        if(sale.getVoucher().length() > 250 ){
            throw new StaleStateException("Mã giảm giá được nhập tối đa 250 kí tự");
        }
        //validate quantity
        try {
            int so =sale.getQuantity();
            if(sale.getQuantity() <= 0 ){
                throw new StaleStateException("Số lượng được nhập phải lớn hơn 0 đơn vị");
            }
            if(sale.getQuantity() >= 1000000 ){
                throw new StaleStateException("Số lượng được nhập tối đa 999999 đơn vị");
            }
        }catch (NumberFormatException e){
            throw new StaleStateException("Số lượng bạn nhập chưa phải là số");
        }
        //validateMucGG



    }

    @Override
    public Page<Sale> getByPage(int pageNumber, int maxRecord, Integer status, String share,String type) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        if (null == type) {
            if (null == share) {
                if (0 == status) {
                    Page<Sale> page = dao.findAll(pageable);
                    return page;
                } else if (1 == status) {//đang diễn ra
                    Page<Sale> page = dao.findGoingOn(pageable);
                    return page;
                } else if (2 == status) {//sắp diễn ra
                    Page<Sale> page = dao.findUpcoming(pageable);
                    return page;
                } else if (3 == status) {//đã dừng
                    Page<Sale> page = dao.findStopped(pageable);
                    return page;
                }
            } else {
                if (0 == status) {
                    Page<Sale> page = dao.findByNameContains(share, pageable);
                    return page;
                } else if (1 == status) {
                    Page<Sale> page = dao.findGoingOnANDName(share, pageable);
                    return page;
                } else if (2 == status) {
                    Page<Sale> page = dao.findUpcomingANDName(share, pageable);
                    return page;
                } else if (3 == status) {
                    Page<Sale> page = dao.findStoppedANDName(share, pageable);
                    return page;
                }
            }
        } else {
            if (0 == status) {
                Page<Sale> page = dao.findByVoucherContains(share, pageable);
                return page;
            } else if (1 == status) {
                Page<Sale> page = dao.findGoingOnANDVoucher(share, pageable);
                return page;
            } else if (2 == status) {
                Page<Sale> page = dao.findUpcomingANDVoucher(share, pageable);
                return page;
            } else if (3 == status) {
                Page<Sale> page = dao.findStoppedANDVoucher(share, pageable);
                return page;
            }
        }
        return null;
    }
}

