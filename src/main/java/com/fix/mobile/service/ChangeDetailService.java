package com.fix.mobile.service;

import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.ProductChange;

import java.util.List;

public interface ChangeDetailService extends GenericService<ChangeDetail, Integer> {
	public void createChangeDetails(Integer id);

	public ChangeDetail findPrChangeDetails(ProductChange idPrChange);
}