package cn.lovelywhite.interestmanager.Data;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {
    public static boolean writeExcel(String fileName,Vector<PriceSum> priceSums) {
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("营收表", 0);
            for (int col = 0; col < priceSums.size(); col++) {
                sheet.addCell(new Label(col, 0,priceSums.get(col).getInterestName()));
                sheet.addCell(new Label(col,1,priceSums.get(col).getInterestPriceSum().toString()));
            }
            workbook.write();
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}