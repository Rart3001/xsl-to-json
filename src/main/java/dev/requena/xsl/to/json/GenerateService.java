/* @#GenerateService.java - 2020
 * Copyright Cencosud.cl, All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * CENCOSUD/CONFIDENTIAL
 */
package dev.requena.xsl.to.json;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/** @author rrequena */
@Component
public class GenerateService {

  Logger logger = LoggerFactory.getLogger(GenerateService.class);

  Map<String, JsonObject> dataForlanguague;
  Map<Integer, String> languaguesIdex;
  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public void generate() throws IOException {

    FileInputStream file = null;
    Workbook workbook = null;

    try {

      dataForlanguague = new HashMap<>();
      languaguesIdex = new HashMap<>();

      Path filePath = Paths.get(ClassLoader.getSystemResource("shoppity_traslations.xlsx").toURI());
      file = new FileInputStream(filePath.toFile());
      workbook = new XSSFWorkbook(file);
      Sheet sheet = workbook.getSheetAt(0);

      int totalRows = sheet.getPhysicalNumberOfRows();
      logger.info("Total Rows = {}", totalRows);

      for (Row row : sheet) {
        String attributeName = "";
        for (Cell cell : row) {

          configureLanguages(cell);

          if (cell.getRowIndex() > 0 && cell.getColumnIndex() == 0) {
            attributeName = cell.getStringCellValue().replace(".", "_").replace("-", "_");
          }

          addAtrributeToData(attributeName, cell);
        }
      }

      logger.info("Total languages =  {}", dataForlanguague.size());
      createFiles();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {

      if (Objects.nonNull(workbook)) {
        workbook.close();
      }

      if (Objects.nonNull(file)) {
        file.close();
      }
    }
  }

  private void configureLanguages(Cell cell) {
    if (cell.getRowIndex() == 0 && cell.getColumnIndex() > 0) {
      String language = cell.getStringCellValue();
      if (!languaguesIdex.containsKey(Integer.valueOf(cell.getColumnIndex()))) {
        languaguesIdex.put(Integer.valueOf(cell.getColumnIndex()), language);
        dataForlanguague.put(language, new JsonObject());
      }
    }
  }

  private void addAtrributeToData(String attributeName, Cell cell) {

    if (ObjectUtils.isEmpty(attributeName) || cell.getColumnIndex() == 0) return;
    if (languaguesIdex.containsKey(Integer.valueOf(cell.getColumnIndex()))) {
      String language = languaguesIdex.get(cell.getColumnIndex());
      JsonObject data = dataForlanguague.get(language);
      data.addProperty(attributeName, StringUtils.capitalize(cell.getStringCellValue()));
    }
  }

  private void createFiles() throws IOException {
      
    for (String language : dataForlanguague.keySet()) {
      logger.info("Creating json file for language = {} ", language);
      JsonObject data = dataForlanguague.get(language);
      logger.info("data =  {}", data);
      Writer writer = new FileWriter("i18n/" + language + ".json");
      gson.toJson(data, writer);
      writer.flush();
      writer.close();
    }
  }
}
