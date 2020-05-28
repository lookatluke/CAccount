/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import com.e.caccount.Model.Person;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

/**
 *
 * @author trito
 */
public class XMLtoEXCEL {

    ////////////////////////////////////////////////////////////////////////////
    //////////////////           INITIALIZATION           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private Person person = Person.getInstance();

    private File file;

    public XMLtoEXCEL(File file) {
        this.file = file;
    }

    private int r;

    private CalendarUtil calendar = new CalendarUtil();
    private Workbook wb = new HSSFWorkbook();

    private CreationHelper createHelper = wb.getCreationHelper();
    private String safeName = WorkbookUtil.createSafeSheetName(calendar.getToday());
    private Sheet sheet = wb.createSheet(safeName);

    private CellStyle styleMainTitle = wb.createCellStyle();
    private Font fontstyleMainTitle = wb.createFont();

    private CellStyle styleCurrentDate = wb.createCellStyle();
    private Font fontstyleCurrentDate = wb.createFont();

    private CellStyle style = wb.createCellStyle();
    private Font font = wb.createFont();

    private CellStyle styleAlignment = wb.createCellStyle();
    private Font font1 = wb.createFont();

    private CellStyle styleTableTitle = wb.createCellStyle();
    private Font fontstyleTableTitle = wb.createFont();

    private CellStyle styleBlank = wb.createCellStyle();

    private CellStyle styleTotalSumValue = wb.createCellStyle();
    private Font fontstyleTotalSumValue = wb.createFont();

    private CellStyle styleTotalSundayValue = wb.createCellStyle();
    private Font fontstyleTotalSundayValue = wb.createFont();

    private CellStyle styleTotalSum = wb.createCellStyle();
    private Font fontstyleTotalSum = wb.createFont();

    private CellStyle styleOfferingType = wb.createCellStyle();
    private Font fontOffeiringType = wb.createFont();

    private CellStyle styleOfferingTypes2 = wb.createCellStyle();
    private Font fontOffeiringTypes2 = wb.createFont();

    private CellStyle styleOfferingTypes = wb.createCellStyle();
    private Font fontOffeiringTypes = wb.createFont();

    private CellStyle styleSign = wb.createCellStyle();
    private Font fontstyleSign = wb.createFont();

    private CellStyle styleSumofTypes = wb.createCellStyle();
    private Font fontstyleSumofTypes = wb.createFont();

    public void setCellAndFontStyle() {
        setFontStyle(fontstyleMainTitle, (short) 16, "Georgia", true);
        setCellStyle(styleMainTitle, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, fontstyleMainTitle, null, null, null, false);

        setFontStyle(fontstyleCurrentDate, (short) 12, "Georgia", true);
        setCellStyle(styleCurrentDate, HorizontalAlignment.LEFT, VerticalAlignment.TOP, fontstyleCurrentDate, null, null, null, false);

        setFontStyle(font, (short) 9, "Georgia", true);
        setCellStyle(style, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, font, BorderStyle.THIN, null, null, true);

        setFontStyle(font1, (short) 9, "Georgia", true);
        setCellStyle(styleAlignment, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, font1, BorderStyle.THIN, null, null, false);

        setFontStyle(fontstyleTableTitle, (short) 9, "Georgia", true);
        setCellStyle(styleTableTitle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, fontstyleTableTitle, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND, false);

        setCellStyle(styleBlank, null, null, null, BorderStyle.THIN, null, null, false);

        setFontStyle(fontstyleTotalSumValue, (short) 9, "Georgia", true);
        setCellStyle(styleTotalSumValue, null, VerticalAlignment.CENTER, fontstyleTotalSumValue, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND, true);

        setFontStyle(fontstyleTotalSundayValue, (short) 9, "Georgia", true);
        setCellStyle(styleTotalSundayValue, null, VerticalAlignment.CENTER, fontstyleTotalSundayValue, BorderStyle.THIN, null, null, true);

        setFontStyle(fontstyleTotalSum, (short) 9, "Georgia", true);
        setCellStyle(styleTotalSum, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, fontstyleTotalSum, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND, false);

        setFontStyle(fontOffeiringType, (short) 9, "Georgia", true);
        setCellStyle(styleOfferingType, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, fontOffeiringType, BorderStyle.THIN, null, null, false);

        setFontStyle(fontOffeiringTypes2, (short) 11, "Georgia", true);
        setCellStyle(styleOfferingTypes2, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, fontOffeiringTypes2, null, null, null, false);

        setFontStyle(fontOffeiringTypes, (short) 9, "Georgia", true);
        setCellStyle(styleOfferingTypes, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, fontOffeiringTypes, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND, false);

        setFontStyle(fontstyleSign, (short) 10, "Georgia", true);
        setCellStyle(styleSign, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, fontstyleSign, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND, false);

        setFontStyle(fontstyleSumofTypes, (short) 9, "Georgia", true);
        setCellStyle(styleSumofTypes, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, fontstyleSumofTypes, BorderStyle.THIN, null, null, false);
    }

    public void setFontStyle(Font font, short height, String fontName, boolean TF) {
        font.setFontHeightInPoints(height);
        font.setFontName(fontName);
        font.setBold(TF);
    }

    public void setCellStyle(CellStyle cellStyle, HorizontalAlignment Halignment, VerticalAlignment Valignment, Font font, BorderStyle Bstyle,
            IndexedColors indexColor, FillPatternType FPtype, boolean SetDataFormat) {

        short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");

        if (SetDataFormat) {
            cellStyle.setDataFormat(format);
        }
        if (Halignment != null) {
            cellStyle.setAlignment(Halignment);
        }
        if (Valignment != null) {
            cellStyle.setVerticalAlignment(Valignment);
        }
        if (font != null) {
            cellStyle.setFont(font);
        }
        if (Bstyle != null) {
            cellStyle.setBorderTop(Bstyle);
            cellStyle.setBorderBottom(Bstyle);
            cellStyle.setBorderRight(Bstyle);
            cellStyle.setBorderLeft(Bstyle);
        }
        if (indexColor != null) {
            cellStyle.setFillForegroundColor(indexColor.getIndex());
        }
        if (FPtype != null) {
            cellStyle.setFillPattern(FPtype);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               START                //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void startXMLtoEXCEL() {
        setCellAndFontStyle();

        setWeekTitle();

        setMiddleAreaTitle();
        setMiddelArea();
        setRvalue();

        setTypesSumTitle();
        setTypesSumSubTitle();
        setTypesSum();

        setColumnSize();

        saveXMLtoExcel();
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////             UPPER AREA             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void setWeekTitle() {
        Row dateTitle = sheet.createRow(0);
        dateTitle.setHeight((short) 600);
        Cell cellDateTitle = dateTitle.createCell(0);
        cellDateTitle.setCellValue(createHelper.createRichTextString(calendar.getCurrentWeek() + "주차 헌금입금전표"));
        cellDateTitle.setCellStyle(styleMainTitle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

        Row date1 = sheet.createRow(1);
        date1.setHeight((short) 600);
        Cell cellDate = date1.createCell(0);
        cellDate.setCellValue(createHelper.createRichTextString(calendar.getToday()));
        cellDate.setCellStyle(styleCurrentDate);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
    }

    public void setTypesSumTitle() {
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
        Row row3 = sheet.createRow(2);
        row3.setHeight((short) 400);
        Cell cellOfferingNames = row3.createCell(0);
        for (int i = 1; i < 4; i++) {
            Cell tempCellOfferingNames = row3.createCell(i);
            tempCellOfferingNames.setCellStyle(styleOfferingTypes2);
        }
        cellOfferingNames.setCellValue(createHelper.createRichTextString("■ 항목별 헌금 현황"));
        cellOfferingNames.setCellStyle(styleOfferingTypes2);

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));
        Cell cellCash = row3.createCell(5);
        for (int i = 6; i < 8; i++) {
            Cell tempCellOfferingNames = row3.createCell(i);
            tempCellOfferingNames.setCellStyle(styleOfferingTypes2);
        }
        cellCash.setCellValue(createHelper.createRichTextString("■ 헌금 금액별 현황"));
        cellCash.setCellStyle(styleOfferingTypes2);

        Cell Sign = row3.createCell(r - 5);
        Sign.setCellStyle(styleSign);
        Sign.setCellValue(createHelper.createRichTextString("결\n\n재"));

        for (int i = r - 3; i < r - 2; i++) {
            Cell tempCellOfferingNames = row3.createCell(i);
            tempCellOfferingNames.setCellStyle(styleOfferingTypes);
        }
        Cell Sign1 = row3.createCell(r - 4);
        Sign1.setCellValue(createHelper.createRichTextString("재정담당"));
        Sign1.setCellStyle(styleOfferingTypes);

        for (int i = r - 1; i < r; i++) {
            Cell tempCellOfferingNames = row3.createCell(i);
            tempCellOfferingNames.setCellStyle(styleOfferingTypes);
        }
        Cell Sign2 = row3.createCell(r - 2);
        Sign2.setCellValue(createHelper.createRichTextString("재정부장"));
        Sign2.setCellStyle(styleOfferingTypes);

        for (int i = r + 1; i < r + 2; i++) {
            Cell tempCellOfferingNames = row3.createCell(i);
            tempCellOfferingNames.setCellStyle(styleOfferingTypes);
        }
        Cell Sign3 = row3.createCell(r);
        Sign3.setCellValue(createHelper.createRichTextString("당회장"));
        Sign3.setCellStyle(styleOfferingTypes);
    }

    public void setTypesSumSubTitle() {
        Row row4 = sheet.createRow(3);
        Cell row4_1 = row4.createCell(0);
        row4_1.setCellValue(createHelper.createRichTextString("헌금항목명"));
        row4_1.setCellStyle(styleOfferingTypes);
        Cell row4_2 = row4.createCell(1);
        row4_2.setCellValue(createHelper.createRichTextString("금액"));
        row4_2.setCellStyle(styleOfferingTypes);
        Cell row4_3 = row4.createCell(2);
        row4_3.setCellValue(createHelper.createRichTextString("헌금항목명"));
        row4_3.setCellStyle(styleOfferingTypes);
        Cell row4_4 = row4.createCell(3);
        row4_4.setCellValue(createHelper.createRichTextString("금액"));
        row4_4.setCellStyle(styleOfferingTypes);
        Cell row4_5 = row4.createCell(5);
        row4_5.setCellValue(createHelper.createRichTextString("금액현황"));
        row4_5.setCellStyle(styleOfferingTypes);
        Cell row4_6 = row4.createCell(6);
        row4_6.setCellValue(createHelper.createRichTextString("수량"));
        row4_6.setCellStyle(styleOfferingTypes);
        Cell row4_7 = row4.createCell(7);
        row4_7.setCellValue(createHelper.createRichTextString("금액"));
        row4_7.setCellStyle(styleOfferingTypes);

        Cell tempSing1 = row4.createCell(r - 5);
        tempSing1.setCellStyle(styleSign);

        for (int g = r - 5; g < r + 2; g++) {
            Cell extraTempCell = row4.createCell(g);
            extraTempCell.setCellStyle(styleBlank);
        }
    }

    public void setTypesSum() {
        int j = 0;
        boolean canImakeTypeSum = true;
        for (int k = 0; k < getK(); k++) {
            Row tempRow = sheet.createRow(k + 4);

            //Amount Sum for each Type            
            for (int i = 0; i < 2; i++) {
                if (i % 2 == 1 && j < person.getTypeList().size() && canImakeTypeSum) {
                    Cell tempCell1 = tempRow.createCell(2);
                    Cell tempCell2 = tempRow.createCell(3);
                    setTypeSumCell(tempCell1, tempCell2, j);
                } else if (i % 2 == 0 && j < person.getTypeList().size() && canImakeTypeSum) {
                    Cell tempCell1 = tempRow.createCell(0);
                    Cell tempCell2 = tempRow.createCell(1);
                    setTypeSumCell(tempCell1, tempCell2, j);
                }

                if (i % 2 == 0 && j >= person.getTypeList().size() && canImakeTypeSum) {
                    sheet.addMergedRegion(new CellRangeAddress(k + 4, k + 4, 0, 2));
                    Cell cellOfferingSum = tempRow.createCell(0);
                    for (int l = 1; l < 4; l++) {
                        Cell tempCellOfferingNames = tempRow.createCell(l);
                        tempCellOfferingNames.setCellStyle(styleTotalSumValue);
                        if (l == 3) {
                            tempCellOfferingNames.setCellValue(person.getTypeSum());
                        }
                    }
                    cellOfferingSum.setCellValue(createHelper.createRichTextString("헌금 총 합계"));
                    cellOfferingSum.setCellStyle(styleOfferingTypes);
                    canImakeTypeSum = false;
                    break;
                } else if (canImakeTypeSum) {
                    j++;
                }
            }

            //Coin and Bills Setting
            if (k < 6) {
                Cell tempRow_1 = tempRow.createCell(5);
                tempRow_1.setCellStyle(styleSumofTypes);
                Cell tempRow_2 = tempRow.createCell(6);
                tempRow_2.setCellStyle(styleTotalSundayValue);
                Cell tempRow_3 = tempRow.createCell(7);
                tempRow_3.setCellStyle(styleTotalSundayValue);

                switch (k) {
                    case 0:
                        tempRow_1.setCellValue(createHelper.createRichTextString("오만원권"));
                        tempRow_2.setCellValue(person.getOhManwon());
                        tempRow_3.setCellValue(person.getOhManwon() * 50000);
                        break;
                    case 1:
                        tempRow_1.setCellValue(createHelper.createRichTextString("일만원권"));
                        tempRow_2.setCellValue(person.getManwon());
                        tempRow_3.setCellValue(person.getManwon() * 10000);
                        break;
                    case 2:
                        tempRow_1.setCellValue(createHelper.createRichTextString("오천원권"));
                        tempRow_2.setCellValue(person.getOhChoenwon());
                        tempRow_3.setCellValue(person.getOhChoenwon() * 5000);
                        break;
                    case 3:
                        tempRow_1.setCellValue(createHelper.createRichTextString("일천원권"));
                        tempRow_2.setCellValue(person.getChoenwon());
                        tempRow_3.setCellValue(person.getChoenwon() * 1000);
                        break;
                    case 4:
                        tempRow_1.setCellValue(createHelper.createRichTextString("오백원"));
                        tempRow_2.setCellValue(person.getOhBaekwon());
                        tempRow_3.setCellValue(person.getOhBaekwon() * 500);
                        break;
                    case 5:
                        tempRow_1.setCellValue(createHelper.createRichTextString("일백원"));
                        tempRow_2.setCellValue(person.getBaekwon());
                        tempRow_3.setCellValue(person.getBaekwon() * 100);
                        break;
                    default:
                        break;
                }
            }

            if (k == 6) {
                Cell tempRow_1 = tempRow.createCell(5);
                tempRow_1.setCellStyle(styleOfferingTypes);
                Cell tempRow_2 = tempRow.createCell(6);
                tempRow_2.setCellStyle(styleTotalSumValue);
                Cell tempRow_3 = tempRow.createCell(7);
                tempRow_3.setCellStyle(styleTotalSumValue);

                tempRow_1.setCellValue(createHelper.createRichTextString("집계"));
                tempRow_2.setCellValue(person.getNumberOfCoinAndBills());
                tempRow_3.setCellValue(person.getSumOfCoinsAndBills());
            }

            //Approval line setting
            if (k < 4) {
                Cell tempSing2 = tempRow.createCell(r - 5);
                tempSing2.setCellStyle(styleSign);

                for (int g = r - 5; g < r + 2; g++) {
                    Cell extraTempCell = tempRow.createCell(g);
                    extraTempCell.setCellStyle(styleBlank);
                }
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(3, 7, r - 4, r - 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, r - 2, r - 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 7, r, r + 1));

        sheet.addMergedRegion(new CellRangeAddress(2, 7, r - 5, r - 5));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, r - 4, r - 3));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, r - 2, r - 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, r, r + 1));

    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////            MIDDLE AREA             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void setMiddleAreaTitle() {
        int currentRow = getK() + 4;
        Row valueBeforeTitle = sheet.createRow(currentRow);
        valueBeforeTitle.setHeight((short) 600);
        sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 0, 2));
        for (int i = 1; i < 3; i++) {
            Cell tempCellOfferingNames = valueBeforeTitle.createCell(i);
            tempCellOfferingNames.setCellStyle(styleOfferingTypes2);
        }
        Cell cellValueBeforeTitle = valueBeforeTitle.createCell(0);
        cellValueBeforeTitle.setCellValue(createHelper.createRichTextString("■ 개인별 헌금현황"));
        cellValueBeforeTitle.setCellStyle(styleOfferingTypes2);
    }

    private Map<Integer, Row> RowMap = new HashMap<>();
    private int tempIncreaseMent = 0;

    public void setMiddelArea() {
        int currentRow = getK() + 5;
        int name_row = 0;
        int amount_row = 1;

        Row titleRow = sheet.createRow(currentRow);
        Row nameRow = sheet.createRow(currentRow + 1);
        for (int i = 0; i < person.getTypeList().size(); i++) {
            if (!person.getTypeList().get(i).equals("주정헌금")) {
                sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, tempIncreaseMent, tempIncreaseMent + 1));
                Cell cell5 = titleRow.createCell(tempIncreaseMent);
                cell5.setCellStyle(styleTableTitle);

                Cell cell6 = titleRow.createCell(tempIncreaseMent + 1);
                cell6.setCellStyle(styleTableTitle);
                cell5.setCellValue(createHelper.createRichTextString(person.getTypeList().get(i)));

                Cell cell1 = nameRow.createCell(tempIncreaseMent);
                Cell cell2 = nameRow.createCell(tempIncreaseMent + 1);
                setTitleCells(cell1, cell2);

                int isForty = 0;
                for (int k = 0; k < person.getUserData(person.getTypeList().get(i)).size(); k++) {
                    if ((isForty / 43.0) < 1) {
                        if (checkRow(currentRow + 2 + isForty)) {
                            Cell name_cell = RowMap.get(currentRow + 2 + isForty).createCell(name_row);
                            Cell amount_cell = RowMap.get(currentRow + 2 + isForty).createCell(amount_row);

                            setNameAmountCells(name_cell, amount_cell, i, k);
                        } else {
                            Row tempRow = sheet.createRow(currentRow + 2 + isForty);
                            Cell name_cell = tempRow.createCell(name_row);
                            Cell amount_cell = tempRow.createCell(amount_row);

                            setNameAmountCells(name_cell, amount_cell, i, k);

                            RowMap.put(currentRow + 2 + isForty, tempRow);
                        }
                        isForty++;
                    } else {
                        if (isForty % 43 == 0) {
                            tempIncreaseMent += 2;
                            name_row += 2;
                            amount_row += 2;

                            sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, tempIncreaseMent, tempIncreaseMent + 1));
                            Cell title_1 = titleRow.createCell(tempIncreaseMent);
                            title_1.setCellStyle(styleTableTitle);
                            Cell title_2 = titleRow.createCell(tempIncreaseMent + 1);
                            title_2.setCellStyle(styleTableTitle);

                            title_1.setCellValue(createHelper.createRichTextString(person.getTypeList().get(i)));

                            Cell name = nameRow.createCell(tempIncreaseMent);
                            Cell amount = nameRow.createCell(tempIncreaseMent + 1);
                            setTitleCells(name, amount);

                        }
                        isForty = MakeBelowForty(k);
                        k--;
                    }
                }

                if (checkRow(currentRow + 2 + 43)) {
                    Cell StringCell = RowMap.get(currentRow + 2 + 43).createCell(tempIncreaseMent);
                    Cell cell = RowMap.get(currentRow + 2 + 43).createCell(tempIncreaseMent + 1);
                    setTotlaSumCell(StringCell, cell, i);
                } else {
                    Row TotalSumRow = sheet.createRow(currentRow + 2 + 43);
                    Cell StringCell = TotalSumRow.createCell(tempIncreaseMent);
                    Cell cell = TotalSumRow.createCell(tempIncreaseMent + 1);
                    setTotlaSumCell(StringCell, cell, i);

                    RowMap.put(currentRow + 2 + 43, TotalSumRow);
                }

                name_row += 2;
                amount_row += 2;
                tempIncreaseMent += 2;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////            PAGE SETTING            //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void setColumnSize() {
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);

        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 2000);
        sheet.setColumnWidth(7, 3000);

        sheet.setColumnWidth(13, 1800);

        sheet.setColumnWidth(14, 1800);
        sheet.setColumnWidth(15, 2000);
        sheet.setColumnWidth(16, 1800);
        sheet.setColumnWidth(17, 2000);
        sheet.setColumnWidth(18, 1800);
        sheet.setColumnWidth(19, 2000);

        sheet.getPrintSetup().setLandscape(true);
        sheet.getPrintSetup().setFooterMargin(10.0D);
        sheet.getPrintSetup().setHeaderMargin(10.0D);
        sheet.setMargin((short) 2, 0.1D);
        sheet.setMargin((short) 3, 0.1D);
        sheet.setMargin((short) 0, 0.1D);
        sheet.setMargin((short) 1, 0.1D);
        sheet.getPrintSetup().setScale((short) 76);
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               METHODS              //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public int MakeBelowForty(int k) {
        if (k % 43 == 0) {
            return 0;
        }
        while (k < 43) {
            k = k - 43;
        }
        return k;
    }

    public void setTotlaSumCell(Cell total, Cell amount, int i) {
        total.setCellStyle(styleTotalSum);
        total.setCellValue(createHelper.createRichTextString("총계"));

        amount.setCellStyle(styleTotalSumValue);
        amount.setCellValue(person.getSelectedTypeSUM(person.getTypeList().get(i)));
    }

    public void setTitleCells(Cell name_cell, Cell amount_cell) {
        name_cell.setCellStyle(styleTableTitle);
        name_cell.setCellValue(createHelper.createRichTextString("성명"));

        amount_cell.setCellStyle(styleTableTitle);
        amount_cell.setCellValue(createHelper.createRichTextString("금액"));
    }

    public void setNameAmountCells(Cell name_cell, Cell amount_cell, int i, int k) {
        name_cell.setCellStyle(styleAlignment);
        name_cell.setCellValue(person.getUserData(person.getTypeList().get(i)).get(k).getName());

        amount_cell.setCellStyle(style);
        amount_cell.setCellValue(person.getUserData(person.getTypeList().get(i)).get(k).getAmount());
    }

    public boolean checkRow(int row) {
        if (RowMap.containsKey(row)) {
            return true;
        } else {
            return false;
        }
    }

    public void setRvalue() {
        if (tempIncreaseMent < 17) {
            r = 16;
        } else {
            r = tempIncreaseMent - 2;
        }
    }

    public void setTypeSumCell(Cell type, Cell amount, int j) {
        type.setCellValue(createHelper.createRichTextString(person.getTypeList().get(j)));
        type.setCellStyle(styleSumofTypes);

        amount.setCellValue(person.getSelectedTypeSUM(person.getTypeList().get(j)));
        amount.setCellStyle(styleTotalSundayValue);
    }

    public int getK() {
        int temp = (int) Math.ceil(person.getTypeList().size() / 2.0);
        if (temp < 7) {
            return 7;
        } else {
            return temp;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////           ONE TYPE EXPORT          //////////////////////
    ////////////////////////////////////////////////////////////////////////////   
    public void setOneTypeList(String type) {
        setCellAndFontStyle();
        setWeekTitle();
        setBodyArea(type);
        saveXMLtoExcel();
    }

    private int OneTypeCurrentRow;

    public void setBodyArea(String type) {
        OneTypeCurrentRow = 3;

        Row titleRow = sheet.createRow(OneTypeCurrentRow);
        Row nameRow = sheet.createRow(OneTypeCurrentRow + 1);

        //TYPE name and title
        sheet.addMergedRegion(new CellRangeAddress(OneTypeCurrentRow, OneTypeCurrentRow, 0, 1));
        Cell cell5 = titleRow.createCell(0);
        cell5.setCellStyle(styleTableTitle);

        Cell cell6 = titleRow.createCell(1);
        cell6.setCellStyle(styleTableTitle);
        cell5.setCellValue(createHelper.createRichTextString(type));

        Cell cell1 = nameRow.createCell(0);
        Cell cell2 = nameRow.createCell(1);
        setTitleCells(cell1, cell2);

        //BODY
        OneTypeCurrentRow += 2;
        for (int k = 0; k < person.getUserData(type).size(); k++) {
            Row tempRow = sheet.createRow(OneTypeCurrentRow);
            Cell name_cell = tempRow.createCell(0);
            Cell amount_cell = tempRow.createCell(1);

            name_cell.setCellStyle(styleAlignment);
            name_cell.setCellValue(person.getUserData(type).get(k).getName());

            amount_cell.setCellStyle(style);
            amount_cell.setCellValue(person.getUserData(type).get(k).getAmount());
            OneTypeCurrentRow++;
        }

        //TOTAL VALUE
        Row TotalSumRow = sheet.createRow(OneTypeCurrentRow);
        Cell StringCell = TotalSumRow.createCell(0);
        Cell cell = TotalSumRow.createCell(1);

        StringCell.setCellStyle(styleTotalSum);
        StringCell.setCellValue(createHelper.createRichTextString("총계"));

        cell.setCellStyle(styleTotalSumValue);
        cell.setCellValue(person.getSelectedTypeSUM(type));
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////            SAVE SETTING            //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void saveXMLtoExcel() {
        try {
            try (OutputStream fileOut = new FileOutputStream(file)) {
                wb.write(fileOut);
            } catch (Throwable localThrowable) {
                throw localThrowable;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("에러");
            alert.setHeaderText("엑셀 저장오류");
            alert.setContentText("엑셀로 저장할 수 없습니다.");

            alert.showAndWait();
        }
    }

}
