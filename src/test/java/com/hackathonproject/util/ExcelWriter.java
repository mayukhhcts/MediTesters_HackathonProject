package com.hackathonproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {

    private static final Logger log = LogManager.getLogger(ExcelWriter.class);
    private static final String FILE_PATH = "target/generatedData.xlsx";

    public static synchronized void writeHospitals(List<String> hospitals) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Hospitals");
            sheet.createRow(0).createCell(0).setCellValue("Hospital Name");
            int rowNum = 1;
            for (String hospital : hospitals) {
                sheet.createRow(rowNum++).createCell(0).setCellValue(hospital);
            }
            save(workbook);
            log.info("Written {} hospitals to Excel", hospitals.size());
        } catch (IOException e) {
            log.error("Failed to write hospitals to Excel: {}", e.getMessage());
        }
    }

    public static synchronized void writeCities(List<String> cities) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Top Cities");
            sheet.createRow(0).createCell(0).setCellValue("City Name");
            int rowNum = 1;
            for (String city : cities) {
                sheet.createRow(rowNum++).createCell(0).setCellValue(city);
            }
            save(workbook);
            log.info("Written {} cities to Excel", cities.size());
        } catch (IOException e) {
            log.error("Failed to write cities to Excel: {}", e.getMessage());
        }
    }

    private static void save(Workbook workbook) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
        } catch (IOException e) {
            log.error("Failed to save Excel file: {}", e.getMessage());
        }
    }
}