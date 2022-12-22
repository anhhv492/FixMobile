package com.fix.mobile.helper;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Color;
import com.fix.mobile.repository.ColorRepository;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.service.CategoryService;
import com.fix.mobile.service.FileManagerService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ExcelHelper {
    Logger LOGGER = Logger.getLogger(ExcelHelper.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private ColorRepository colorRepository;

    private Integer idAccessory=null;
    private String name=null;
    private Integer quantity=null;
    private java.sql.Date createDate= Date.valueOf(java.time.LocalDate.now());
    private String color=null;
    private BigDecimal price=null;
    private Boolean status=true;
    private String image=null;
    private String note=null;
    private String folder = "accessories";
    public Boolean readExcel(MultipartFile files) {
        LOGGER.info("readExcel");
        File dir = new File(System.getProperty("user.dir")+"/excels");
        try {
            File savedFile = new File(dir,files.getOriginalFilename());
            if(!dir.exists()) {
                dir.mkdirs();
            }
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
//			FormulaEvaluator fomula =wb.getCreationHelper().createFormulaEvaluator();
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
                            quantity = (int) cc.getNumericCellValue();
                        }
                        if (cc.getColumnIndex() == 2 && cc.getCellType() == CellType.STRING) {
                            System.out.println(cc.getStringCellValue() + ", ");
                            color = cc.getStringCellValue();
                        }
                        if (cc.getColumnIndex() == 3 && cc.getCellType() == CellType.NUMERIC) {
                            System.out.println(cc.getNumericCellValue() + ", ");
                            price = BigDecimal.valueOf(cc.getNumericCellValue());
                        }
                        if (cc.getColumnIndex() == 4 && cc.getCellType() == CellType.STRING) {
                            System.out.println(cc.getStringCellValue() + ", ");
                            note = cc.getStringCellValue();
                        }
                        if (cc.getColumnIndex() == 5 && cc.getCellType() == CellType.STRING) {
                            System.out.println("Cate: "+cc.getStringCellValue());
                            Optional<Category> category =  categoryService.findByName(cc.getStringCellValue());
                            if(!category.isPresent()){
                                System.out.println("khong ton tai");
                                Category category1 = new Category();
                                category1.setName(cc.getStringCellValue());
                                category1.setType(1);
                                categoryService.save(category1);
                                category = categoryService.findByName("Sạc");
                            }else{
                                System.out.println("ton tai");
                            }
                            System.out.println("-- excel: " + name + " " + image + " " + note+" ");
                            Accessory accessory = new Accessory(name,quantity,createDate,color,price,status,note,category.get());
                            accessory.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669087979/kbasp5qdf76f3j02mebr.png");
                            accessoryService.save(accessory);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
