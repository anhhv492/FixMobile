package com.fix.mobile.payload;

import com.fix.mobile.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductRequest {
	private String name;
	private List<MultipartFile> files;
	private String imei;
	private Date createDate;
	private String camera;
	private BigDecimal price;
	private String size;
	private String note;
	private Boolean status;
	private Ram ram;
	private List<Image> listImage;
	private Color color;
	private Capacity capacity;
	private Category category;
}
