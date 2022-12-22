package com.fix.mobile.helper;

import com.fix.mobile.entity.*;
import com.fix.mobile.payload.SaveProductRequest;
import com.fix.mobile.service.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelProducts {
	@Autowired private CategoryService categoryService;
	@Autowired private RamService ramService;

	@Autowired private ProductService productService;
	private Integer idProduct=null;
	private String name=null;
	private java.sql.Date createDate= Date.valueOf(java.time.LocalDate.now());
	@Autowired private ColorService colorService;
	private BigDecimal price=null;
	private int  status= 0;

	private Transient files =null;

	private String size =null;

	private String camera=null;
	private String note=null;

	@Autowired
	private CapacityService capacityService;

	private  String imei =null;
	private Optional<Category> category = null;
	private Optional<Color> colors = null;

	private Optional<Capacity> capacitys =null;

	private Optional<Ram> ram = null;

	// imay
	@Autowired private ImayProductService imayService;
	private Integer idImay = null;
	private String nameImay = null;
	private int  statusImay= 1;
	private Optional<Product> product = null;

	public Boolean readExcel(MultipartFile files) {
		File dir = new File(System.getProperty("user.dir"));
		try {
			File savedFile = new File(dir,files.getOriginalFilename());
			if(!savedFile.exists()){
				files.transferTo(savedFile);
			}else{
				savedFile.delete();
				files.transferTo(savedFile);
			}
			System.out.println(savedFile.getAbsolutePath());
			FileInputStream file = new FileInputStream(savedFile.getAbsolutePath());
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell cc = cellIterator.next();
					if (cc.getRowIndex() > 0) {
						if (cc.getColumnIndex() == 0 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							name = cc.getStringCellValue();
						}
						if (cc.getColumnIndex() == 1 && cc.getCellType() == CellType.NUMERIC) {
							System.out.println(cc.getNumericCellValue() + ", ");
							status = Integer.valueOf((int) cc.getNumericCellValue());
						}
						if (cc.getColumnIndex() == 2 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							camera = cc.getStringCellValue();
						}
						if (cc.getColumnIndex() == 3 && cc.getCellType() == CellType.NUMERIC) {
							System.out.println(cc.getNumericCellValue() + ", ");
							price = BigDecimal.valueOf(cc.getNumericCellValue());
						}
						if (cc.getColumnIndex() == 4 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							size = cc.getStringCellValue();
						}
						if (cc.getColumnIndex() == 5 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							note = cc.getStringCellValue();
						}
						if (cc.getColumnIndex() == 6 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							imei = cc.getStringCellValue();
						}
						if (cc.getColumnIndex() == 7 && cc.getCellType() == CellType.STRING) {
							System.out.println("Cate: "+cc.getStringCellValue());
							colors = colorService.findByName(cc.getStringCellValue());
							if (!colors.isPresent()){
								System.out.println("ddax ton tai");
								Color  color= new Color();
								color.setName(cc.getStringCellValue());
								colorService.save(color);
							}else;

						}
						if (cc.getColumnIndex() == 8 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							category = categoryService.findByName(cc.getStringCellValue());
							if(!category.isPresent()){
								System.out.println("khong ton tai");
								Category category1 = new Category();
								category1.setName(cc.getStringCellValue());
								category1.setType(1);
								categoryService.save(category1);
							}else{
								System.out.println("ton tai");
							}
						}
						if (cc.getColumnIndex() == 9 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							ram = ramService.findByName(cc.getStringCellValue());
							if (!ram.isPresent()){
								System.out.println("ddax ton tai");
								Ram r= new Ram();
								r.setName(cc.getStringCellValue());
								ramService.save(r);
							}else;
						}
						if (cc.getColumnIndex() == 10 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							capacitys = capacityService.findByName(cc.getStringCellValue());
							if (!capacitys.isPresent()){
								System.out.println("không ton tai");
								Capacity capa= new Capacity();
								capa.setName(cc.getStringCellValue());
								capacityService.save(capa);
							}else;
							System.out.println("-- excel: " + name + " "  + " " + note+" ");
							Product product= new Product( name,  imei, createDate,
									camera,  price,  size,
									note,  status,  null, colors.get(),
									null,null, null, null);
							productService.save(product);
						}


					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			return false;
		}
		return true;
	}

	public Boolean readExcelImay(MultipartFile files) {
		File dir = new File(System.getProperty("user.dir")+"/excels");
		try {
			File savedFile = new File(dir,files.getOriginalFilename());
			if(!dir.exists()) {
				dir.mkdirs();
			}else if(!savedFile.exists()){
				files.transferTo(savedFile);
			}else{
				savedFile.delete();
				files.transferTo(savedFile);
			}
			System.out.println(savedFile.getAbsolutePath());
			FileInputStream file = new FileInputStream(savedFile.getAbsolutePath());
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell cc = cellIterator.next();
					if (cc.getRowIndex() > 0) {
						if (cc.getColumnIndex() == 0 && cc.getCellType() == CellType.STRING) {
							System.out.println(cc.getStringCellValue() + ", ");
							nameImay = cc.getStringCellValue();
						}
						if (cc.getColumnIndex() == 1  && cc.getCellType() == CellType.STRING) {
							System.out.println("product " + cc.getStringCellValue() + ", ");
							product =  productService.findByName(cc.getStringCellValue());
							if (!product.isPresent()){
								System.out.println("produtcssssssss "+product);
								System.out.println("chưa có sản phẩm này");
							}else if(product==null){
								System.out.println("đã null");
							}
							ImayProduct imay = new ImayProduct(nameImay,statusImay,product.get());
							imayService.save(imay);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			return false;
		}
		return true;
	}

}
