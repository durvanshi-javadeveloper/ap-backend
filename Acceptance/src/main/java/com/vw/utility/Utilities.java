package com.vw.utility;

import com.vw.model.LevelInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import com.vw.model.ProjectDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;


import static com.vw.utility.Constants.*;

public class Utilities {
    static Logger log = LoggerFactory.getLogger(Utilities.class);

    public static FileOutputStream generateReport(ProjectDetails projectDetails, LocalDate localDate) throws IOException, InvalidFormatException {
        log.info("inside the generateReport() Method");
        XWPFDocument accptanceReport = new XWPFDocument();
        XWPFTable table = accptanceReport.createTable(TWENTY, FIVE);
        table.setCellMargins(0, 0, 0, 0);

        XWPFParagraph p1 = table.getRow(ZERO).getCell(ZERO).addParagraph();
        XWPFRun r1 = p1.createRun();
        FileInputStream fis = new FileInputStream("img_1.png");
        r1.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG, "img_1", Units.toEMU(70),
                Units.toEMU(70));

        table.getRow(ZERO).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(3));
        XWPFParagraph p2 = table.getRow(ZERO).getCell(ONE).addParagraph();
        XWPFRun r2 = p2.createRun();
        FileInputStream fis2 = new FileInputStream("img_2.png");
        r2.addPicture(fis2, XWPFDocument.PICTURE_TYPE_PNG, "img_2", Units.toEMU(340),
                Units.toEMU(70));

        XWPFParagraph p3 = table.getRow(ZERO).getCell(TWO).addParagraph();
        XWPFRun r3 = p3.createRun();
        FileInputStream fis3 = new FileInputStream("img.png");
        r3.addPicture(fis3, XWPFDocument.PICTURE_TYPE_PNG, "img", Units.toEMU(70),
                Units.toEMU(70));

        XWPFTableRow row = table.getRow(ZERO);
        int cellIndex = row.getTableCells().size() - 1;
        for (int i = cellIndex; i >= cellIndex - 1; i--) {
            row.getCell(i).getCTTc().newCursor().removeXml();
        }
        table.setTableAlignment(TableRowAlign.CENTER);

        CTVMerge vmerge = CTVMerge.Factory.newInstance();
        vmerge.setVal(STMerge.RESTART);
        table.getRow(ONE).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmerge);
        table.getRow(ONE).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmerge);

        CTVMerge vmerge1 = CTVMerge.Factory.newInstance();
        vmerge1.setVal(STMerge.CONTINUE);
        table.getRow(TWO).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmerge1);
        table.getRow(TWO).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmerge1);

        CTVMerge vmergeTwo = CTVMerge.Factory.newInstance();
        vmergeTwo.setVal(STMerge.RESTART);
        table.getRow(THREE).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmergeTwo);
        table.getRow(THREE).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmergeTwo);

        CTVMerge vmergeThree = CTVMerge.Factory.newInstance();
        vmergeThree.setVal(STMerge.CONTINUE);
        table.getRow(FOUR).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmergeThree);
        table.getRow(FOUR).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmergeThree);


        //Project Caption
        setCaption(ONE, ZERO, PROJECT, table);
        //Set Project Value
        setCellValue(ONE, ONE, projectDetails.getProjectName(), table);
        //Set Date Caption
        setCaption(ONE, TWO, DATE, table);
        //Set Date Value
        String ukDate = new SimpleDateFormat(UK_PATTERN, Locale.getDefault()).format(new Date());
        setCellValue(TWO, TWO, ukDate, table);
        //Set Billing Period Caption
        setCaption(ONE, THREE, BILLING_PERIOD, table);
        //Set Billing Period Value
        String padded = String.format("%-40s", " to");
        String splitDate = projectDetails.getFromDate() + padded + projectDetails.getToDate();
        setCellValue(TWO, THREE, splitDate, table);
        //Set Agreement Number Caption
        setCaption(ONE, FOUR, AGREEMENT_NUMBER, table);
        //Set Agreement NUmber Value
        setCellValue(TWO, FOUR, projectDetails.getAgrmntNumber(), table);
        //Set Responsible Person Caption
        setCaption(THREE, ZERO, RESPONSIBLE_PERSON, table);
        //Set Responsible Person Value
        setCellValue(THREE, ONE, projectDetails.getRespPersonal(), table);
        //Set Order Number Caption
        setCaption(THREE, TWO, ORDER_NUMBER, table);
        //Set Order Number Value
        setCellValue(FOUR, TWO, projectDetails.getOrdrNumber(), table);
        //Set Service Provider Caption
        String SERVICE_PROVIDER = SERVICE.concat(String.format("%-60s", " ")).concat(PROVIDER);
        setCaption(THREE, THREE, SERVICE_PROVIDER, table);
        //Set Service Provider Value
        setCellValue(FOUR, THREE, projectDetails.getSrvcProvider(), table);
        //Set Service Receiver Caption
        setCaption(THREE, FOUR, SERVICE_RECEIVER, table);
        //Set Service Receiver Value
        setCellValue(FOUR, FOUR, projectDetails.getSrvcReceiver(), table);

        for (int i = 5; i <= 12; i++) {
            table.getRow(i).getCell(1).getCTTc().addNewTcPr().addNewGridSpan()
                    .setVal(BigInteger.valueOf(4));
            XWPFTableRow rowSix = table.getRow(i);
            int cellSixIndex = rowSix.getTableCells().size() - 1;
            for (int j = cellSixIndex; j >= cellSixIndex - 2; j--) {
                rowSix.getCell(j).getCTTc().newCursor().removeXml();
            }
        }
        //Set Sr Caption
        setCaption(FIVE, ZERO, SR, table);
        //Set Task List Caption
        setCaption(FIVE, ONE, TASK_LIST, table);
        String[] proDesc = projectDetails.getPrjctDesc().split("\\.");
        int COUNT = 6;
        int srNumber = 1;
        for (String proData : proDesc) {
            setCaption(COUNT, ZERO, srNumber + "", table);
            setCellValue(COUNT, ONE, proData, table);
            COUNT++;
            srNumber++;
        }
        //Merge
        table.getRow(THIRTEEN).getCell(FOUR).setWidth("0%");
        table.getRow(THIRTEEN).getCell(ZERO).setWidth("20%");
        table.getRow(THIRTEEN).getCell(ONE).setWidth("40%");
        table.getRow(THIRTEEN).getCell(TWO).setWidth("35%");
        table.getRow(THIRTEEN).getCell(THREE).setWidth("20%");
        mergeCells(THIRTEEN, THREE, STMerge.RESTART, table);
        mergeCells(FOURTEEN, THREE, STMerge.RESTART, table);
        mergeCells(FIFTEEN, THREE, STMerge.RESTART, table);
        mergeCells(SIXTEEN, THREE, STMerge.RESTART, table);

        mergeCells(THIRTEEN, FOUR, STMerge.CONTINUE, table);
        mergeCells(FOURTEEN, FOUR, STMerge.CONTINUE, table);
        mergeCells(FIFTEEN, FOUR, STMerge.CONTINUE, table);
        mergeCells(SIXTEEN, FOUR, STMerge.CONTINUE, table);
        //Set Total Budget Caption
        setCaption(THIRTEEN, ONE, TOTAL_BUDGET, table);
        //Set Current Month Budget Caption
        setCaption(THIRTEEN, TWO, CURRENT_MONTH_BDGT, table);
        //Set Current Month Budget Value
        setCellValue(FOURTEEN, TWO, String.valueOf(projectDetails.getSrvcMonthlyCost()), table);
        //Set Remaining Budget Caption
        setCaption(THIRTEEN, THREE, REMAINING_BDGT, table);
        //Set Remaining Budget Value
        setCellValue(FOURTEEN, THREE, String.valueOf(projectDetails.getSrvcRemainBdgt()), table);
        //Set Service Cost Caption
        setCaption(FOURTEEN, ZERO, SERVICE_COST, table);
        //Set Service Cost Value
        setCellValue(FOURTEEN, ONE, String.valueOf(projectDetails.getSrvcCost()), table);
        //Set Miscellaneous Caption
        setCaption(FIFTEEN, ZERO, MISCELLANEOUS, table);
        //Set Miscellaneous Value
        setCellValue(FIFTEEN, ONE, String.valueOf(projectDetails.getMiscCost()), table);
        //Set Miscellaneous Monthly Budget Value
        setCellValue(FIFTEEN, TWO, String.valueOf(projectDetails.getMiscMonthlyBdgt()), table);
        //Set Miscellaneous Remaining Budget Value
        setCellValue(FIFTEEN, THREE, String.valueOf(projectDetails.getMiscRemainBdgt()), table);
        //Set Total Caption
        setCaption(SIXTEEN, ZERO, TOTAL, table);
        setCellValue(SIXTEEN, ONE, String.valueOf(projectDetails.getTotalCost()), table);
        setCellValue(SIXTEEN, TWO, String.valueOf(projectDetails.getTotalMonthlyBdgt()), table);
        setCellValue(SIXTEEN, THREE, String.valueOf(projectDetails.getTotalRemainBdgt()), table);
        //Row Number Seventeen is merged into 2 columns
        table.getRow(SEVENTEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(5));
        XWPFTableRow seventeen = table.getRow(SEVENTEEN);
        int cellSeventeenIndex = seventeen.getTableCells().size() - 1;
        for (int i = cellSeventeenIndex; i >= cellSeventeenIndex - 3; i--) {
            seventeen.getCell(i).getCTTc().newCursor().removeXml();
        }

        StringBuilder commonData = new StringBuilder("Billing details : ");
        Set<LevelInfo> levelInfo = projectDetails.getLevelInfo();
        for (LevelInfo lData : levelInfo) {
            if (lData.getLevel() == 1) {
                commonData.append("L1 :" + lData.getMember() + " (" + lData.getPrice() + ")");
            } else if (lData.getLevel() == 2) {
                commonData.append("L2 :" + lData.getMember() + " (" + lData.getPrice() + ")");
            }
        }
        String DECLAIRMENT = "We hereby  declare that the information on this Acceptance Criteria is correct and the invoice for month of " + ukDate + " will be billed for amount of €" + projectDetails.getTotalMonthlyBdgt() + "." + String.format("%-90s", " ")
                .concat(String.valueOf(commonData));
        //Set Declairation Caption
        setCaption(SEVENTEEN, ZERO, DECLAIRMENT, table);

        table.getRow(EIGHTEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(2));
        table.getRow(EIGHTEEN).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(5));

        XWPFTableRow eighteenSix = table.getRow(EIGHTEEN);
        int cellTwentyIndex = eighteenSix.getTableCells().size() - 1;
        for (int i = cellTwentyIndex; i >= cellTwentyIndex - 2; i--) {
            eighteenSix.getCell(i).getCTTc().newCursor().removeXml();
        }

        table.getRow(NINETEEN).getCell(ZERO).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(2));
        table.getRow(NINETEEN).getCell(ONE).getCTTc().addNewTcPr().addNewGridSpan()
                .setVal(BigInteger.valueOf(5));
        XWPFTableRow twentyRow = table.getRow(NINETEEN);
        int index = twentyRow.getTableCells().size() - 1;
        for (int i = index; i >= index - 2; i--) {
            twentyRow.getCell(i).getCTTc().newCursor().removeXml();
        }
        CTVMerge vmergeTwenty = CTVMerge.Factory.newInstance();
        vmergeTwo.setVal(STMerge.RESTART);
        table.getRow(EIGHTEEN).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmergeTwenty);
        table.getRow(EIGHTEEN).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmergeTwenty);

        CTVMerge vmergeNew = CTVMerge.Factory.newInstance();
        vmergeThree.setVal(STMerge.CONTINUE);
        table.getRow(NINETEEN).getCell(ZERO).getCTTc().addNewTcPr().setVMerge(vmergeNew);
        table.getRow(NINETEEN).getCell(ONE).getCTTc().addNewTcPr().setVMerge(vmergeNew);

        setCellValue(EIGHTEEN, ZERO, projectDetails.getMngrName(), table);
        setCellValue(EIGHTEEN, ONE, projectDetails.getClientName(), table);

        String date = new SimpleDateFormat(PATTERN, Locale.getDefault()).format(new Date());
        FileOutputStream fstream = new FileOutputStream(ACCEPTANCE_REPORT + projectDetails.getProjectName()
                + "-" + localDate.getMonth() + "-" + localDate.getYear() + ".docx");
        accptanceReport.write(fstream);
        return fstream;
    }

    public static void setCaption(int rowNum, int cellNum, String caption, XWPFTable table) {
        XWPFParagraph paragraphArray = table.getRow(rowNum).getCell(cellNum).getParagraphArray(0);
        if (rowNum == SEVENTEEN && cellNum == ZERO) {
            paragraphArray.setAlignment(ParagraphAlignment.LEFT);
        } else {
            paragraphArray.setAlignment(ParagraphAlignment.CENTER);
        }
        XWPFRun capProject = paragraphArray.createRun();
        capProject.setBold(true);
        capProject.setText(caption);

    }

    public static void setCellValue(int rowNum, int cellNum, String fieldValue, XWPFTable table) {
        XWPFParagraph para = table.getRow(rowNum).getCell(cellNum).getParagraphArray(0);
        para.setAlignment(ParagraphAlignment.CENTER);
        para.createRun().setText(fieldValue);
    }

    public static void mergeCells(int inCell, int mergeCell, STMerge.Enum mergeValue, XWPFTable table) {
        CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
        hMerge1.setVal(mergeValue);
        table.getRow(inCell).getCell(mergeCell).getCTTc().addNewTcPr().setHMerge(hMerge1);
    }

    public static LocalDate dateConvert(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_FORMAT);
        SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_FORMATE);
        LocalDate formattedDate = null;
        try {
            Date date = inputFormat.parse(inputDate);
            String result = outputFormat.format(date);
            System.out.println("Result : " + result);
            DateTimeFormatter df = DateTimeFormatter.ofPattern(OUTPUT_FORMATE);
            formattedDate = LocalDate.parse(result, df);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static void calculateOtherMonths(ProjectDetails proData) {
        Set<LevelInfo> levelInfo = proData.getLevelInfo();
        Optional<Double> reduce = levelInfo.stream().map(e -> e.getPrice() * e.getMember()).collect(Collectors.toList())
                .stream().reduce(Double::sum);

        proData.setSrvcRemainBdgt(proData.getSrvcRemainBdgt() - reduce.get());
        proData.setTotalMonthlyBdgt(proData.getSrvcMonthlyCost() + proData.getMiscMonthlyBdgt());
        proData.setMiscRemainBdgt(proData.getMiscRemainBdgt() - proData.getMiscMonthlyBdgt());
        proData.setTotalRemainBdgt(proData.getSrvcRemainBdgt() + proData.getMiscRemainBdgt());
    }

    public static void prepairePersistanceData(ProjectDetails proDetails,
                                               ProjectDetails proPersist) {
        proDetails.setGeneratedDate(dateConvert(proDetails.getGeneratedDate()).plusMonths(1).toString());
        proPersist.setAgrmntNumber(proDetails.getAgrmntNumber());
        proPersist.setBrandName(proDetails.getBrandName());
        proPersist.setDepartment(proDetails.getDepartment());
        proPersist.setSubDeprtmt(proDetails.getSubDeprtmt());
        proPersist.setProjectName(proDetails.getProjectName());
        proPersist.setGeneratedDate(proDetails.getGeneratedDate());
        proPersist.setFromDate(proDetails.getFromDate());
        proPersist.setToDate(proDetails.getToDate());
        proPersist.setOrdrNumber(proDetails.getOrdrNumber());
        proPersist.setRespPersonal(proDetails.getRespPersonal());
        proPersist.setSrvcProvider(proDetails.getSrvcProvider());
        proPersist.setSrvcReceiver(proDetails.getSrvcReceiver());
        proPersist.setPrjctDesc(proDetails.getPrjctDesc());

        //CostDetails
        proPersist.setSrvcCost(proDetails.getSrvcCost());
        proPersist.setSrvcMonthlyCost(proDetails.getSrvcMonthlyCost());
        proPersist.setSrvcRemainBdgt(proDetails.getSrvcRemainBdgt());
        proPersist.setMiscCost(proDetails.getMiscCost());
        proPersist.setMiscMonthlyBdgt(proDetails.getMiscMonthlyBdgt());
        proPersist.setMiscRemainBdgt(proDetails.getMiscRemainBdgt());
        proPersist.setTotalCost(proDetails.getTotalCost());
        proPersist.setTotalMonthlyBdgt(proDetails.getTotalMonthlyBdgt());
        proPersist.setTotalRemainBdgt(proDetails.getTotalRemainBdgt());
        proPersist.setMiscPricing(proDetails.getMiscPricing());
        proPersist.setMngrName(proDetails.getMngrName());
        proPersist.setClientName(proDetails.getClientName());
        prepaireLevelInfo(proPersist,proDetails);

    }

    private static void prepaireLevelInfo(ProjectDetails proPersist, ProjectDetails proDetails) {
        Set<LevelInfo> levelSet = new HashSet<>();
        for (LevelInfo levelInfo : proDetails.getLevelInfo()) {
            LevelInfo levelData = new LevelInfo();
            levelData.setLevel(levelInfo.getLevel());
            levelData.setMember(levelInfo.getMember());
            levelData.setPrice(levelInfo.getPrice());
            levelSet.add(levelData);
        }
        proPersist.setLevelInfo(levelSet);
    }

}

