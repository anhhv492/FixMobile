package com.fix.mobile.rest.controller;

import com.fix.mobile.model.ReportPaymentModel;
import com.fix.mobile.service.ReportPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/report")
public class ReportPayRestController {
	@Autowired
	private ReportPayService reportPayService;

	@GetMapping(value= "/Pay")
	public List<ReportPaymentModel> index(Pageable page){
		List<ReportPaymentModel> listPayReport = reportPayService.listReport();
		return listPayReport;
	}

	@GetMapping(value="/search")
	public List<ReportPaymentModel> searchData(
			@RequestParam("fullname") String fullName,
			@RequestParam("createdate") String createDate){
		List<ReportPaymentModel> list = reportPayService.searchByNameAndDate(fullName, createDate);
		if(!list.isEmpty()){
			return list;
		}
		return null;
	}
}
