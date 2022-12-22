package com.fix.mobile.repository;

import com.fix.mobile.entity.Image;
import com.fix.mobile.entity.ImayProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Integer> {
	@Query("select i from Image  i where i.productChange = ?1")
	public List<Image> findImageByPr(Integer id);

}