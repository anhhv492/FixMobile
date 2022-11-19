package com.fix.mobile.service;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;

import java.util.List;
import java.util.Optional;

public interface AccessoryService extends GenericService<Accessory, Integer> {
    List<Accessory> findByCategoryAndStatus(Optional<Category> cate);
}