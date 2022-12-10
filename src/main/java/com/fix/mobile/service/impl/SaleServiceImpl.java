package com.fix.mobile.service.impl;

import org.hibernate.StaleStateException;
import com.fix.mobile.repository.SaleDetailRepository;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.service.SaleService;
import com.fix.mobile.entity.Sale;
import org.springframework.security.core.Authentication;
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
    public Sale add(Sale salee) {
        vaildate_NULL_DEFAULT(salee);
        Sale sale = setNULL(salee);
        sale.setIdSale(null);
        sale.setCreateTime(new Date());
        sale.setUserCreate(1); //cuongnd edit
        sale.setUpdateTime(null);
        sale.setUserUpdate(null);
        return dao.save(sale);
    }

    @Override
    public Sale update(Sale sale) {
        vaildate_NULL_DEFAULT(sale);
        Sale saleUpdate = dao.findByIdSale(sale.getIdSale());
        saleUpdate.setPercentSale(sale.getPercentSale());
        saleUpdate.setMoneySale(sale.getMoneySale());
        saleUpdate.setQuantity(sale.getQuantity());
        saleUpdate.setCreateStart(sale.getCreateStart());
        saleUpdate.setDetailSale(sale.getDetailSale());
        if(saleUpdate.getCreateStart().after(new Date())){
            saleUpdate.setCreateEnd(sale.getCreateEnd());
        }
        if(saleUpdate.getCreateEnd().after(new Date())){
            saleUpdate.setCreateEnd(sale.getCreateEnd());
        }
        saleUpdate = setNULL(saleUpdate);
        return dao.save(saleUpdate);
    }

    @Override
    public void delete(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public List<Sale> getall(Integer status) {
        return null;
    }

    @Override
    public Sale findByid(Integer id) {
        return dao.findByIdSale(id);
    }

    @Override
    public Integer getLimit1Sale() {
        return dao.getLimit1Sale();
    }

    private void validate_Coincide(Sale sale) { //check trùng
        if(sale.getIdSale() == null){
            if(sale.getVoucher() != null){
                if(dao.findByVoucher(sale.getVoucher()).isPresent()){
                    throw new StaleStateException("voucher đã có mời nhập voucher khác");
                }
            }
        }
    }

    private void validate_Date(Sale sale) {
        SimpleDateFormat viewDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        Date day = new Date();
        if (sale.getIdSale() == null) {
            if (sale.getCreateStart().before(day)) {
                throw new StaleStateException("Thời gian bắt đầu không thể trước " + viewDate.format(day));
            }
        } else {
            Sale saleud = dao.findById(sale.getIdSale()).get();
            if(saleud.getCreateStart().before(day)) {
                if (saleud.getCreateStart() == (sale.getCreateStart())) {
                    throw new StaleStateException("Không thể sửa Thời gian bắt đầu");
                }
                if (sale.getCreateEnd().before(day)) {
                    throw new StaleStateException("Thời gian kết thúc không thể trước " + viewDate.format(day));
                }
            }
        }
        if (sale.getCreateStart().equals(sale.getCreateEnd())){
            throw new StaleStateException("Thời gian kết thúc không thể bằng Thời gian bắt đầu");
        }
        if (sale.getCreateStart().after(sale.getCreateEnd())){
            throw new StaleStateException("Thời gian kết thúc không thể trước Thời gian bắt đầu");
        }
    }

    private void vaildate_NULL_DEFAULT(Sale sale){
        if(sale.getTypeSale()==null){
            throw new StaleStateException("Bạn phải chọn loại giảm giá");
        }
        if(sale.getName()==null){
            throw new StaleStateException("Tên chương trình sai định dạng");
        }
        if(sale.getDiscountMethod() == null){
            throw new StaleStateException("Bạn phải chọn Phương thức giảm giá");
        }
        if(sale.getDiscountMethod() == 0){
            if(sale.getVoucher() == null ){
                throw new StaleStateException("Mã giảm giá sai định dạng");
            }
        }
        if(sale.getDiscountType() == null){
            throw new StaleStateException("Bạn phải chọn Kiểu giảm giá");
        }
        if(sale.getDiscountType() == 0){
            if(sale.getMoneySale() == null){
                throw new StaleStateException("Mức giảm giá sai định dạng");
            }
        }
        if(sale.getDiscountType() == 1){
            if(sale.getPercentSale() == null){
                throw new StaleStateException("Mức giảm giá sai định dạng");
            }
        }
        if(sale.getQuantity() == null){
            throw new StaleStateException("Số lượng sai định dạng");
        }
        if(sale.getCreateStart() == null){
            throw new StaleStateException("Thời gian bắt đầu sai định dạng");
        }
        if(sale.getCreateEnd() == null){
            throw new StaleStateException("Thời gian kết thúc sai định dạng");
        }
        if(sale.getTypeSale() == 2 ){
            if(sale.getValueMin() == null ){
                throw new StaleStateException("Giá trị đơn hàng tối thiểu sai định dạng");
            }
        }
        if(sale.getTypeSale() == 3 ){
            if(sale.getUserType() == null ){
                throw new StaleStateException("Bạn phải chọn đối tượng giảm giá");
            }
        }
        validate_Date(sale);
        validate_Coincide(sale);
    }

    @Override
    public Page<Sale> getByPage(int pageNumber, int maxRecord, Integer status, String share,String type) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        if (null == type) {
            if (null == share) {
                if (0 == status) {
                    Page<Sale> page = dao.findAllSale(pageable);
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
    private Sale setNULL(Sale sale){
        if(sale.getDiscountMethod()==1){
            sale.setVoucher(null);
        }
        if(sale.getDiscountType()==0){
            sale.setPercentSale(null);
        }
        if(sale.getDiscountType()==1){
            sale.setMoneySale(null);
        }
        if(sale.getTypeSale()!=2){
            sale.setValueMin(null);
        }
        if(sale.getTypeSale()!=3){
            sale.setUserType(null);
        }
        return sale;
    }
}

