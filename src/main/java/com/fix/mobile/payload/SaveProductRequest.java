package com.fix.mobile.payload;

import com.fix.mobile.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductRequest {
	private Integer idProduct;
	private String name;
	private List<MultipartFile> files;
	private Date createDate;
	private String camera;
	private BigDecimal price;
	private String size;
	private String note;
	private int status;
	private Ram ram;
	private List<Image> listImage;
	private Color color;
	private Capacity capacity;
	private Category category;

}
