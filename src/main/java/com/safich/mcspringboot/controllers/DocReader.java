package com.safich.mcspringboot.controllers;

import com.safich.mcspringboot.models.DataStorage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class DocReader {
    private final DataStorage storage;

    @Autowired
    public DocReader(DataStorage storage) {
        this.storage = storage;
    }

    public void readData(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workBook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workBook.getSheet("Денежные потоки");

        XSSFRow row = sheet.getRow(2);
        int c = 2;
        int y = 2021;

        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setCapEx(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(4);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setTotalRevenue(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(9);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setTotalCost(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(13);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setSeveranceTax(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(14);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setDeprecation(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(15);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setGenRunCosts(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(17);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setOpProfit(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(19);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setIntPays(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(21);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setProfBefTax(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(23);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setIncomeTax(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(25);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setNetProfit(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(27);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setCashFlow(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(28);
        c = 2;
        y = 2021;
        for (int i = 0; i < storage.getReportPeriod(); i++) {
            XSSFCell cell = row.getCell(c);
            storage.setDiscCashFlow(y, cell.getNumericCellValue());
            c++;
            y++;
        }

        row = sheet.getRow(30);
        XSSFCell npvCell = row.getCell(2);
        storage.setNpv(npvCell.getNumericCellValue());

        row = sheet.getRow(31);
        XSSFCell irrCell = row.getCell(2);
        storage.setIrr(irrCell.getNumericCellValue());

        row = sheet.getRow(32);
        XSSFCell piCell = row.getCell(2);
        storage.setPi(piCell.getNumericCellValue());
    }
}
