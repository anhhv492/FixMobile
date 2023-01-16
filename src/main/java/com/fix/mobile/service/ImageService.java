package com.fix.mobile.service;


import com.fix.mobile.dto.ImeiProductResponDTO;
import com.fix.mobile.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageService extends GenericService<Image, Integer> {
	public List<Image> findImageByPr(Integer id);


}