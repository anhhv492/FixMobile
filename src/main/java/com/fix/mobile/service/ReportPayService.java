package com.fix.mobile.service;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.model.ReportPaymentModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReportPayService  {

    List<ReportPaymentModel> listReport();

    List<ReportPaymentModel> searchByNameAndDate(String fullName , String createDate);
}