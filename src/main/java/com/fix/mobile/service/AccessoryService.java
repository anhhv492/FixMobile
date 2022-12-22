package com.fix.mobile.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.accessory.AccessoryDTO;
import com.fix.mobile.dto.accessory.AccessoryResponDTO;
import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Sale;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccessoryService extends GenericService<Accessory, Integer> {
    List<Accessory> findByCategoryAndStatus(Optional<Category> cate);

    Page<Accessory> getByPage(int pageNumber, int maxRecord, String share);
    List<Accessory> findAccessory();

    AccessoryResponDTO save(AccessoryDTO accessoryDTO);

    AccessoryResponDTO update(Integer id, AccessoryDTO accessoryDTO);

    List<Accessory> getTop4();

    Page<Accessory> getByPage(int pageNumber, int maxRecord, JsonNode findProcuctAll);
}