package com.fix.mobile.service;


import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.ProductChange;

import java.util.List;

public interface ProductChangeService extends GenericService<ProductChange, Integer> {
	public List<ProductChange> listProductChange();

}