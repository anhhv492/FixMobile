package com.fix.mobile.service.impl;

import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.repository.OrderRepository;
import org.hibernate.StaleStateException;
import com.fix.mobile.repository.SaleDetailRepository;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.service.SaleService;
import com.fix.mobile.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    SaleRepository dao;

    @Autowired
    SaleDetailRepository dao1;
    @Autowired
    OrderRepository dao2;

    @Autowired
    AccountRepository dao3;

    @Override
    public Sale add(Sale salee) {
        vaildate_NULL_DEFAULT(salee);
        Sale sale = setNULL(salee);
        sale.setIdSale(null);
        sale.setCreateTime(new Date());
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
        saleUpdate.setUserUpdate(sale.getUserUpdate());
        saleUpdate.setUpdateTime(new Date());
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
    public Sale updateQuantity(Sale sale) {
        return dao.save(sale);
    }

    @Override
    public void delete(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public Integer findSaleApply(Integer id) {
        return dao.findSaleApply(id);
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

    @Override
    public Sale getBigSale(String userName, BigDecimal money, Integer idPrd, Integer idAcsr) {
        Sale bigSale = new Sale();
        Sale bigSaleMoney = new Sale();
        Sale bigSalePercent = new Sale();
        List<Sale> listMoneySale = new ArrayList<>();
        List<Sale> listPercentSale = new ArrayList<>();
        if( null != userName){
            Sale getBigSaleUserName = dao.getBigSale_UserName(userName,money);
            if( null != getBigSaleUserName ){
                if(null != getBigSaleUserName.getMoneySale()){
                    listMoneySale.add(getBigSaleUserName);
                }else{
                    listPercentSale.add(getBigSaleUserName);
                }
            }

        }
        if(money!=null){
            Sale getBigSaleALLSHOP = dao.getBigSale_ALLSHOP(money);
            if(null != getBigSaleALLSHOP){
                if(null != getBigSaleALLSHOP.getMoneySale()){
                    listMoneySale.add(getBigSaleALLSHOP);
                }else{
                    listPercentSale.add(getBigSaleALLSHOP);
                }
            }
        }
        if(null!=idAcsr){
            Sale getBigSaleAccessory = dao.getBigSale_Accessory(idAcsr);
            if(null != getBigSaleAccessory){
                if(null != getBigSaleAccessory.getMoneySale()){
                    listMoneySale.add(getBigSaleAccessory);
                }else{
                    listPercentSale.add(getBigSaleAccessory);
                }
            }
        }
        if(null != idPrd){
            Sale getBigSaleProducts = dao.getBigSale_Products(idPrd);
            if(null != getBigSaleProducts){
                if(null != getBigSaleProducts.getMoneySale()){
                    listMoneySale.add(getBigSaleProducts);
                }else{
                    listPercentSale.add(getBigSaleProducts);
                }
            }
        }
        if(0 < listMoneySale.size()){
            bigSaleMoney = listMoneySale.get(0);
            if(1 <= listMoneySale.size()) {
                for (int i = 0; i < listMoneySale.size(); i++) {
                    if (listMoneySale.get(i).getMoneySale().compareTo(bigSaleMoney.getMoneySale()) > 0) {
                        bigSaleMoney = listMoneySale.get(i);
                    }
                }
            }
        }
        if(0 < listPercentSale.size()){
            bigSalePercent = listPercentSale.get(0);
            if(1 <= listPercentSale.size()) {
                for (int i = 0; i < listPercentSale.size(); i++) {
                    if (listPercentSale.get(i).getPercentSale() > bigSalePercent.getPercentSale()) {
                        bigSalePercent = listPercentSale.get(i);
                    }
                }
            }
        }
        if(new Sale().equals(bigSaleMoney)){
            if (new Sale().equals(bigSalePercent)){
                bigSale=null;
            }else {
                bigSale = bigSalePercent;
            }
        }else {
            if (new Sale().equals(bigSalePercent)){
                bigSale=bigSaleMoney;
            }else{
                double percentSale = bigSalePercent.getPercentSale() / 100;
                BigDecimal convertPercentToMoney = money.multiply(BigDecimal.valueOf(percentSale));
                if (bigSaleMoney.getMoneySale().compareTo(convertPercentToMoney) > 0) {
                    bigSale = bigSaleMoney;
                } else {
                    bigSale = bigSalePercent;
                }
            }
        }
        return bigSale;
    }

    @Override
    public Sale getBigSale_Order(BigDecimal money) {
        return dao.getBigSale_Order(money);
    }

    @Override
    public void addApply_Sale( Integer idSale, String userName) {
         dao.addSaleApply(idSale,userName);
    }

    @Override
    public List<Sale> getSaleByVoucher(String userName, BigDecimal money, List<Integer> idPrd, List<Integer> idAcsr) {
        List<Sale> listSaleByVoucher = new ArrayList<>();
        if(null != dao.getVoucherAllShop()){
            for (Sale x  : dao.getVoucherAllShop()) {
                if(!new Sale().equals(x)){
                    listSaleByVoucher.add(x);
                }
            }

        }
        if(null!=idAcsr){
            for (int y : idAcsr ) {
                if(null!= dao.getVoucherAccessory(y)){
                    for (Sale x : dao.getVoucherAccessory(y)) {
                        if (!new Sale().equals(x)) {
                            listSaleByVoucher=SaleCheck(listSaleByVoucher,x.getIdSale());
                            listSaleByVoucher.add(x);
                        }
                    }
                }
            }
        }
        if(null!=idPrd){
            for (int y : idPrd ) {
                if(null!= dao.getVoucherProduct(y)){
                    for (Sale x : dao.getVoucherProduct(y)) {
                        if (!new Sale().equals(x)) {
                            listSaleByVoucher=SaleCheck(listSaleByVoucher,x.getIdSale());
                            listSaleByVoucher.add(x);
                        }
                    }
                }
            }
        }
        if(null!= dao.getVoucherOrder(money)){
            for (Sale x  : dao.getVoucherOrder(money)) {
                if(!new Sale().equals(x)){
                    listSaleByVoucher.add(x);
                }
            }
        }
        if(null!=userName) {
            if (null != dao.getVoucherAccount(userName)) {
                for (Sale x : dao.getVoucherAccount(userName)) {
                    if (!new Sale().equals(x)) {
                        listSaleByVoucher.add(x);
                    }
                }
            }
            if (null == dao2.findAllByAccount(dao3.findByUsername(userName))) {
                if (null != dao.getVoucherAccountNew()) {
                    for (Sale x : dao.getVoucherAccountNew()) {
                        if (!new Sale().equals(x)) {
                            listSaleByVoucher.add(x);
                        }
                    }
                }
            }
            if(null!= dao.getVoucherUsed(userName)){
                for (Integer x  : dao.getVoucherUsed(userName)) {
                    for(int i=0;i<listSaleByVoucher.size();i++){
                        if(x==listSaleByVoucher.get(i).getIdSale()){
                            listSaleByVoucher.remove(i);
                        }
                    }
                }
            }
        }
        return listSaleByVoucher;
    }

    private List<Sale> SaleCheck(List<Sale> listSale, Integer idCheck){
        if(0!=listSale.size()||null==listSale){
            for (int i=0; i<listSale.size();i++){
                if(listSale.get(i).getIdSale()==idCheck){
                    listSale.remove(i);
                }
            }
            return listSale;
        }else{
            return listSale;
        }
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
        if(sale.getName()==null || sale.getName().length()==0){
            throw new StaleStateException("Tên chương trình không được bỏ trống");
        }
        if(sale.getDiscountMethod() == null){
            throw new StaleStateException("Bạn phải chọn Phương thức giảm giá");
        }
        if(sale.getDiscountMethod() == 0){
            if(sale.getVoucher() == null ||sale.getVoucher().length()==0){
                throw new StaleStateException("Mã giảm giá sai định dạng hãy nhập trên 5 kí tự không in hoa, không bao gồm kí tự đặc biệt và khoảng trắng");
            }
        }
        if(sale.getDiscountType() == null){
            throw new StaleStateException("Bạn phải chọn Kiểu giảm giá");
        }
        if(sale.getDiscountType() == 0){
            if(sale.getMoneySale() == null){
                throw new StaleStateException("Mức giảm giá sai định dạng hãy nhập lớn hơn 10.000");
            }
        }
        if(sale.getDiscountType() == 1){
            if(sale.getPercentSale() == null){
                throw new StaleStateException("Mức giảm giá sai định dạng hãy nhập từ 1 đến 100");
            }
        }
        if(sale.getQuantity() == null){
            throw new StaleStateException("Số lượng sai định dạng hãy nhập lớn hơn 1");
        }
        if(sale.getCreateStart() == null){
            throw new StaleStateException("Thời gian bắt đầu sai định dạng hãy nhập tháng / ngày / năm và nằm trong khoảng 12 tháng gần đây");
        }
        if(sale.getCreateEnd() == null){
            throw new StaleStateException("Thời gian kết thúc sai định dạng hãy nhập tháng / ngày / năm và nằm trong khoảng 12 tháng gần đây");
        }
        if(sale.getTypeSale() == 2 ){
            if(sale.getValueMin() == null ){
                throw new StaleStateException("Giá trị đơn hàng tối thiểu sai định dạng hãy nhập lớn hơn hoặc bằng 0");
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
        if(sale.getTypeSale()==2){
            sale.setDiscountMethod(0);
        }
        return sale;
    }
}

