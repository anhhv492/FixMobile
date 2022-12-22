package com.fix.mobile.service.impl;

import com.fix.mobile.model.ReportPaymentModel;
import com.fix.mobile.repository.ReportPayRepository;
import com.fix.mobile.service.ReportPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportPayImpl implements ReportPayService {

    @Autowired
    private ReportPayRepository repositoryReport;

    @Override
    public List<ReportPaymentModel> listReport() {
        return (List<ReportPaymentModel>) repositoryReport.findAll();
    }

    @Override
    public List<ReportPaymentModel> searchByNameAndDate(String fullName, String createDate) {
        return repositoryReport.findByFullnameAndcreateDate(fullName,createDate);
    }
}