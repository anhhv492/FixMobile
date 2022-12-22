package com.fix.mobile.helper;

import com.fix.mobile.entity.Order;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class WriteExcel {
    Logger LOGGER = Logger.getLogger(WriteExcel.class);
    public static void writeExcel(List<Order> list) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("FixMobile");
        XSSFRow row = null;
        Cell cell = null;
        row=sheet.createRow(0);
        cell=row.createCell(0);
        cell.setCellValue("Mã đơn hàng");
        cell=row.createCell(1);
        cell.setCellValue("Tài khoản");
        cell=row.createCell(2);
        cell.setCellValue("Họ tên");
        cell=row.createCell(3);
        cell.setCellValue("Số điện thoại");
        cell=row.createCell(4);
        cell.setCellValue("Địa chỉ");
        cell=row.createCell(5);
        cell.setCellValue("Ngày mua");
        cell=row.createCell(6);
        cell.setCellValue("Ngày nhận");
        cell=row.createCell(7);
        cell.setCellValue("Tiền ship (VNĐ)");
        cell=row.createCell(8);
        cell.setCellValue("Tổng giá (VNĐ)");
        cell=row.createCell(9);
        cell.setCellValue("Trạng thái");
        cell=row.createCell(10);
        cell.setCellValue("Ghi chú");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            row=sheet.createRow(i+1);
            cell=row.createCell(0,CellType.STRING);
            cell.setCellValue(list.get(i).getIdOrder());
            cell=row.createCell(1,CellType.STRING);
            cell.setCellValue(list.get(i).getAccount().getUsername());
            cell=row.createCell(2,CellType.STRING);
            cell.setCellValue(list.get(i).getPersonTake());
            cell=row.createCell(3,CellType.STRING);
            cell.setCellValue(list.get(i).getPhoneTake());
            cell=row.createCell(4,CellType.STRING);
            cell.setCellValue(list.get(i).getAddress());
            cell=row.createCell(5,CellType.STRING);
            cell.setCellValue(list.get(i).getCreateDate());
            cell=row.createCell(6,CellType.STRING);
            cell.setCellValue(list.get(i).getTimeReceive());
            cell=row.createCell(7,CellType.STRING);
            cell.setCellValue(String.valueOf(list.get(i).getMoneyShip()));
            cell=row.createCell(8,CellType.STRING);
            cell.setCellValue(String.valueOf(list.get(i).getTotal()));
            if(list.get(i).getStatus()==0) {
                cell = row.createCell(9, CellType.STRING);
                if (list.get(i).getStatusBuy() == 0) {
                    cell.setCellValue("Chưa thanh toán");
                }else if(list.get(i).getStatusBuy()==1) {
                    cell.setCellValue("Đã thanh toán");
                }
            }else if(list.get(i).getStatus()==1) {
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue("Xác nhận");
            }else if(list.get(i).getStatus()==2) {
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue("Đang giao hàng");
            }else if(list.get(i).getStatus()==3) {
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue("Hoàn tất giao dịch");
            }else if(list.get(i).getStatus()==4) {
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue("Đã hủy");
            }
            cell=row.createCell(10,CellType.STRING);
            cell.setCellValue(list.get(i).getNote());
        }
        System.out.println("--Successfully write excel--");
        System.out.println(list.size());
        String path = "C:\\FixMobile\\excels";
        String name = "Orders.xlsx";
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path+"\\"+name);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
