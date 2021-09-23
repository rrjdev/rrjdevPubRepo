package readExcelPkg;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import oracle.jdbc.driver.OracleDriver;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcel {
    static String sourceFileName;

    // variables for table:PREMATURED_TRANSACTIONS_REPORT
    static String country;
    static String institution;
    static String buyerName;
    static String buyerId;
    static String eppReference;
    static String eppCurrency;
    static String bprnDRNRefPTR;
    static String transDatePTR;
    static String amountPTR;

    // variables for table:APPROVED_INVOICES_REPORT
    static String bprnDRNRefAIR;
    static String invoiceNumAIR;
    static String invoiceNumPrevAIR = "N/A";
    static String poRefNumAIR;
    static String suppNameAIR;
    static String suppIdAIR;
    static String apprvInvoiceAmntAIR;
    static String credNoteRefNumAIR;
    static String credNoteApplAmntAIR;
    static String apprvInvoiceAdjAmntAIR;
    static String invoiceDateAIR;
    static String invoiceDueDateAIR;
    static String invoiceSettlementDateAIR;
    static String apprvInvoiceUploadDateAIR;
    static String rejectedFlagAIR;
    static String releaseHoldFlagAIR;
    static String paymentRunFlagAIR;
    static String paymentDescriptionAIR;
    static String processedStatusAIR;
    static String errorDetailsAIR;

    // variables for table:CREDIT_NOTE_APPL_REPORT
    static String credNoteRefNumCNAR;
    static String poRefNumCNAR;
    static String invoiceNumCNAR;
    static String suppNameCNAR;
    static String suppIdCNAR;
    static String apprvInvoiceAmntCNAR;
    static String credNoteTotalAmntCNAR;
    static String credNoteApplAmntCNAR;
    static String credNoteBalCNAR;
    static String apprvInvoiceAdjAmntCNAR;
    static String credNoteApplDateCNAR;
    static String credNoteUplDateCNAR;
    static String rejectedFlagCNAR;
    static String releaseHoldFlagCNAR;
    static String createInstallmentFlagCNAR;
    static String paymentRunFlagCNAR;
    static String installmentNumCNAR;
    static String paymentDescriptionCNAR;
    static String processedStatusCNAR;
    static String errorDetailsCNAR;

    static Connection conn = null;
    static PreparedStatement pstmtInsertBuyerPreMatReconcReportTbl = null;
    static PreparedStatement pstmtApprovedInvoicesReportTbl = null;
    static PreparedStatement pstmtCreditNoteApplicationReportTbl = null;

    static Statement stmtFetchPaymentDescriptionAIR = null;
    static Statement stmtUpdatePaymentDescriptionAIR = null;

    static String qryFetchPaymentDescriptionAIR = "";
    static String qryUpdatePaymentDescriptionAIR = "";

    static String sheetName;

    static int eppRefRowNum = 0;
    static int preMatTransRowNum = 0;
    static int apprInvRowNum = 0;
    static int credNoteApplRowNum = 0;

    static String lastRowSetName = "";

    static Sheet sheet;
    static int sheetCount = 0;
    static int sheetIndex = 0;

    static Row row;
    static int rowIndex = 0;
    static int rowCount = 0;

    static DataFormatter dataFormatter;


    static ArrayList eppRefRowStartPosList = new ArrayList();
    static ArrayList eppRefRowEndPosList = new ArrayList();

    static ArrayList preMatTransRowStartPosList = new ArrayList();
    static ArrayList preMatTransRowEndPosList = new ArrayList();

    static ArrayList apprInvRowStartPosList = new ArrayList();
    static ArrayList apprInvRowEndPosList = new ArrayList();

    static ArrayList credNoteApplRowStartPosList = new ArrayList();
    static ArrayList credNoteApplRowEndPosList = new ArrayList();

    static int eppRefRowCount = 0;
    static int preMatTransRowCount = 0;
    static int apprInvRowCount = 0;
    static int credNoteApplRowCount = 0;

    static int startOuterIndex = 0;
    static int endOuterIndex = 0;

    static int startInnerIndex = 0;
    static int endInnerIndex = 0;

    static int minIndexPos = 0;
    static int maxIndexPos = 0;

    static int lastRowPos = 0;

    public static final String xlsFilePath =
        "D:\\Recon Report_2021-05-05-07.17.04.149102.XLS";


    public static void main(String[] args) {
        ReadExcel exrObj = new ReadExcel();
        try {
            exrObj.excelReaderMethod(xlsFilePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void excelReaderMethod(String xlsFilePath) throws IOException,
                                                                    InvalidFormatException,
                                                                    SQLException {

        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        sheetCount = workbook.getNumberOfSheets();
        System.out.println("Workbook has " + sheetCount + " sheets");

        while (sheetIndex < sheetCount) {
            sheet = workbook.getSheetAt(sheetIndex);
            sheetName = sheet.getSheetName();

            System.out.println("==> " + sheetName);

            dataFormatter = new DataFormatter();

            System.out.println("\n\n Iterating over rows and columns \n");

            rowCount = sheet.getLastRowNum();


            while (rowIndex < rowCount) {
                row = sheet.getRow(rowIndex);

                String cellValue =
                    dataFormatter.formatCellValue(row.getCell(1));

                System.out.println("Test >> " + cellValue);


                if (cellValue.equalsIgnoreCase("EPP Reference")) {
                    eppRefRowNum = row.getRowNum();
                    eppRefRowStartPosList.add(eppRefRowNum);

                    eppRefRowNum = eppRefRowNum + 1;
                    eppRefRowEndPosList.add(eppRefRowNum);
                } else if (cellValue.equalsIgnoreCase("Supply Chain Solutions - PreMatured Transactions")) {
                    lastRowSetName = "PreM_Trans";

                    preMatTransRowNum = row.getRowNum() + 4;
                    preMatTransRowStartPosList.add(preMatTransRowNum);
                } else if (cellValue.equalsIgnoreCase("Approved Invoices")) {
                    lastRowSetName = "Appr_Inv";

                    apprInvRowNum = row.getRowNum() + 2;
                    apprInvRowStartPosList.add(apprInvRowNum);
                } else if (cellValue.equalsIgnoreCase("Credit Note Application")) {
                    lastRowSetName = "CredN_Appl";

                    credNoteApplRowNum = row.getRowNum() + 2;
                    credNoteApplRowStartPosList.add(credNoteApplRowNum);
                } else if ((cellValue.contains("Total")) &&
                           (lastRowSetName.equals("PreM_Trans"))) {
                    preMatTransRowNum = row.getRowNum();
                    preMatTransRowEndPosList.add(preMatTransRowNum);
                } else if ((cellValue.contains("Total")) &&
                           (lastRowSetName.equals("Appr_Inv"))) {
                    apprInvRowNum = row.getRowNum();
                    apprInvRowEndPosList.add(apprInvRowNum);
                } else if ((cellValue.contains("Total")) &&
                           (lastRowSetName.equals("CredN_Appl"))) {
                    credNoteApplRowNum = row.getRowNum();
                    credNoteApplRowEndPosList.add(credNoteApplRowNum);
                }

                lastRowPos++;
                rowIndex++;
            }

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }


            String queryInsertBuyerPreMatReconcReportTbl =
                "INSERT INTO user_1.PREMATURED_TRANS_REPORT (SRC_FILE_NAME,COUNTRY,INSTITUTION,BUYER_NAME,BUYER_ID,EPP_REFERENCE,EPP_CURRENCY,BPRN_DRN_REF,TRANS_DATE,AMOUNT) VALUES (?,?,?,?,?,?,?,?,?,?)";

            String queryApprovedInvoicesReportTbl =
                "INSERT INTO user_1.APPROVED_INVOICES_REPORT (SRC_FILE_NAME,BPRN_DRN_REF,INVOICE_NUM,PO_REF_NUM,SUPP_NAME,SUPP_ID,APPRV_INV_AMOUNT,CRED_NOTE_REF_NUM,CRED_NOTE_APPL_AMOUNT,APPRV_INV_ADJ_AMOUNT,INV_DATE,INV_DUE_DATE,INV_SETL_DATE,APPRV_INV_UPL_DATE,REJECTED_FLAG,RELEASE_HOLD_FLAG,PAYMENT_RUN_FLAG,PAYMENT_DESCRIPTION,EPP_REFERENCE,EPP_CURRENCY,PROCESSED_STATUS,ERROR_DETAILS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            String queryCreditNoteApplicationReportTbl =
                "INSERT INTO user_1.CREDIT_NOTE_APPL_REPORT (SRC_FILE_NAME,CRED_NOTE_REF_NUM,PO_REF_NUM,INVOICE_NUM,SUPP_NAME,SUPP_ID,APPRV_INV_AMOUNT,CRED_NOTE_TOTAL_AMOUNT,CRED_NOTE_APPL_AMOUNT,CRED_NOTE_BAL,APPRV_INV_ADJ_AMOUNT,CRED_NOTE_APPL_DATE,CRED_NOTE_UPL_DATE,REJECTED_FLAG,RELEASE_HOLD_FLAG,CREATE_INSTALLMENT_FLAG,PAYMENT_RUN_FLAG,PAYMENT_DESCRIPTION,EPP_REFERENCE,EPP_CURRENCY,PROCESSED_STATUS,ERROR_DETAILS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                DriverManager.registerDriver(new OracleDriver());

                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "user_1", "Pass_1");
                pstmtInsertBuyerPreMatReconcReportTbl =
                        conn.prepareStatement(queryInsertBuyerPreMatReconcReportTbl);
                pstmtApprovedInvoicesReportTbl =
                        conn.prepareStatement(queryApprovedInvoicesReportTbl);
                pstmtCreditNoteApplicationReportTbl =
                        conn.prepareStatement(queryCreditNoteApplicationReportTbl);
            }

            catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            eppRefRowCount = eppRefRowStartPosList.size();

            country =
                    dataFormatter.formatCellValue(sheet.getRow(2).getCell(2));
            institution =
                    dataFormatter.formatCellValue(sheet.getRow(3).getCell(2));
            buyerName =
                    dataFormatter.formatCellValue(sheet.getRow(5).getCell(2));
            buyerId =
                    dataFormatter.formatCellValue(sheet.getRow(6).getCell(2));

            for (int i = 0; i < eppRefRowCount; i++) {

                startOuterIndex = Integer.parseInt(eppRefRowStartPosList.get(i).toString());
                endOuterIndex = Integer.parseInt(eppRefRowEndPosList.get(i).toString());

                if (i < (eppRefRowCount - 1)) {
                    minIndexPos = startOuterIndex;
                    maxIndexPos = Integer.parseInt(eppRefRowEndPosList.get(i + 1).toString());
                } else if (i == (eppRefRowCount - 1)) {
                    minIndexPos = startOuterIndex;
                    maxIndexPos = lastRowPos;
                }


                sourceFileName =
                        "Buyer Prematurity Reconciliation Report XLS2020-05-05-07.17.04.149102.XLS";

                eppReference =
                        dataFormatter.formatCellValue(sheet.getRow(startOuterIndex).getCell(2));
                eppCurrency =
                        dataFormatter.formatCellValue(sheet.getRow(endOuterIndex).getCell(2));

                preMatTransRowCount = preMatTransRowStartPosList.size();

                for (int j = 0; j < preMatTransRowCount; j++) {
                    startInnerIndex = Integer.parseInt(preMatTransRowStartPosList.get(j).toString());
                    endInnerIndex = Integer.parseInt(preMatTransRowEndPosList.get(j).toString());

                    if ((startInnerIndex > minIndexPos) &&
                        (endInnerIndex < maxIndexPos)) {
                        for (int k = startInnerIndex; k < endInnerIndex; k++) {

                            bprnDRNRefPTR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(1));
                            transDatePTR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(2));
                            amountPTR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(3));


                            try {
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(1,
                                                                                sourceFileName);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(2,
                                                                                country);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(3,
                                                                                institution);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(4,
                                                                                buyerName);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(5,
                                                                                buyerId);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(6,
                                                                                eppReference);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(7,
                                                                                eppCurrency);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(8,
                                                                                bprnDRNRefPTR);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(9,
                                                                                transDatePTR);
                                pstmtInsertBuyerPreMatReconcReportTbl.setString(10,
                                                                                amountPTR);

                                pstmtInsertBuyerPreMatReconcReportTbl.execute();

                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }

                apprInvRowCount = apprInvRowStartPosList.size();

                for (int j = 0; j < apprInvRowCount; j++) {
                    startInnerIndex = Integer.parseInt(apprInvRowStartPosList.get(j).toString());
                    endInnerIndex = Integer.parseInt(apprInvRowEndPosList.get(j).toString());

                    //System.out.println("***"+startInnerIndex+"***"+endInnerIndex);

                    if ((startInnerIndex > minIndexPos) &&
                        (endInnerIndex < maxIndexPos)) {
                        for (int k = startInnerIndex; k < endInnerIndex; k++) {

                            bprnDRNRefAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(1));
                            invoiceNumAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(2));
                            poRefNumAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(3));
                            suppNameAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(4));
                            suppIdAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(5));
                            apprvInvoiceAmntAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(6));
                            credNoteRefNumAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(7));
                            credNoteApplAmntAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(8));
                            apprvInvoiceAdjAmntAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(9));
                            invoiceDateAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(10));
                            invoiceDueDateAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(11));
                            invoiceSettlementDateAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(12));
                            apprvInvoiceUploadDateAIR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(13));

                            if ((apprvInvoiceAmntAIR != null) &&
                                !apprvInvoiceAmntAIR.trim().isEmpty()) {
                                rejectedFlagAIR = "N";
                            } else {
                                rejectedFlagAIR = "Y";
                            }

                            if ((invoiceNumAIR != null) &&
                                !invoiceNumAIR.trim().isEmpty()) {
                                invoiceNumPrevAIR = invoiceNumAIR;
                            } else {
                                invoiceNumAIR = invoiceNumPrevAIR;
                            }


                            releaseHoldFlagAIR = "N";
                            paymentRunFlagAIR = "N";

                            if ((bprnDRNRefAIR != null) &&
                                (!bprnDRNRefAIR.trim().isEmpty()) &&
                                (bprnDRNRefAIR != "N/A")) {
                                paymentDescriptionAIR = bprnDRNRefAIR;
                            } else {
                                paymentDescriptionAIR = "";
                            }

                            processedStatusAIR = "";
                            errorDetailsAIR = "";

                            try {
                                pstmtApprovedInvoicesReportTbl.setString(1,
                                                                         sourceFileName);
                                pstmtApprovedInvoicesReportTbl.setString(2,
                                                                         bprnDRNRefAIR);
                                pstmtApprovedInvoicesReportTbl.setString(3,
                                                                         invoiceNumAIR);
                                pstmtApprovedInvoicesReportTbl.setString(4,
                                                                         poRefNumAIR);
                                pstmtApprovedInvoicesReportTbl.setString(5,
                                                                         suppNameAIR);
                                pstmtApprovedInvoicesReportTbl.setString(6,
                                                                         suppIdAIR);
                                pstmtApprovedInvoicesReportTbl.setString(7,
                                                                         apprvInvoiceAmntAIR);
                                pstmtApprovedInvoicesReportTbl.setString(8,
                                                                         credNoteRefNumAIR);
                                pstmtApprovedInvoicesReportTbl.setString(9,
                                                                         credNoteApplAmntAIR);
                                pstmtApprovedInvoicesReportTbl.setString(10,
                                                                         apprvInvoiceAdjAmntAIR);
                                pstmtApprovedInvoicesReportTbl.setString(11,
                                                                         invoiceDateAIR);
                                pstmtApprovedInvoicesReportTbl.setString(12,
                                                                         invoiceDueDateAIR);
                                pstmtApprovedInvoicesReportTbl.setString(13,
                                                                         invoiceSettlementDateAIR);
                                pstmtApprovedInvoicesReportTbl.setString(14,
                                                                         apprvInvoiceUploadDateAIR);
                                pstmtApprovedInvoicesReportTbl.setString(15,
                                                                         rejectedFlagAIR);
                                pstmtApprovedInvoicesReportTbl.setString(16,
                                                                         releaseHoldFlagAIR);
                                pstmtApprovedInvoicesReportTbl.setString(17,
                                                                         paymentRunFlagAIR);
                                pstmtApprovedInvoicesReportTbl.setString(18,
                                                                         paymentDescriptionAIR);
                                pstmtApprovedInvoicesReportTbl.setString(19,
                                                                         eppReference);
                                pstmtApprovedInvoicesReportTbl.setString(20,
                                                                         eppCurrency);
                                pstmtApprovedInvoicesReportTbl.setString(21,
                                                                         processedStatusAIR);
                                pstmtApprovedInvoicesReportTbl.setString(22,
                                                                         errorDetailsAIR);

                                pstmtApprovedInvoicesReportTbl.execute();

                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }


                credNoteApplRowCount = credNoteApplRowStartPosList.size();

                for (int j = 0; j < credNoteApplRowCount; j++) {
                    startInnerIndex = Integer.parseInt(credNoteApplRowStartPosList.get(j).toString());
                    endInnerIndex = Integer.parseInt(credNoteApplRowEndPosList.get(j).toString());

                    //System.out.println("***"+startInnerIndex+"***"+endInnerIndex);

                    if ((startInnerIndex > minIndexPos) &&
                        (endInnerIndex < maxIndexPos)) {
                        for (int k = startInnerIndex; k < endInnerIndex; k++) {

                            credNoteRefNumCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(1));
                            poRefNumCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(2));
                            invoiceNumCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(3));
                            suppNameCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(4));
                            suppIdCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(5));
                            apprvInvoiceAmntCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(6));
                            credNoteTotalAmntCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(7));
                            credNoteApplAmntCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(8));
                            credNoteBalCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(9));
                            apprvInvoiceAdjAmntCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(10));
                            credNoteApplDateCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(11));
                            credNoteUplDateCNAR =
                                    dataFormatter.formatCellValue(sheet.getRow(k).getCell(12));

                            if ((credNoteApplAmntCNAR != null) &&
                                !credNoteApplAmntCNAR.trim().isEmpty()) {
                                rejectedFlagCNAR = "N";
                            } else {
                                rejectedFlagCNAR = "Y";
                            }

                            releaseHoldFlagCNAR = "N";
                            createInstallmentFlagCNAR = "N";
                            paymentRunFlagCNAR = "N";
                            installmentNumCNAR = "";

                            if ((credNoteApplDateCNAR != null) &&
                                !credNoteApplDateCNAR.trim().isEmpty() &&
                                credNoteApplDateCNAR.contains("/") &&
                                !credNoteApplDateCNAR.contains("N/A")) {
                                int findInitCharPos =
                                    credNoteApplDateCNAR.indexOf("/");
                                int findLastCharPos =
                                    credNoteApplDateCNAR.indexOf("/",
                                                                 findInitCharPos +
                                                                 1);

                                paymentDescriptionCNAR =
                                        "FC_" + credNoteApplDateCNAR.substring(findLastCharPos +
                                                                               3) +
                                        credNoteApplDateCNAR.substring(findInitCharPos +
                                                                       1,
                                                                       findLastCharPos) +
                                        credNoteApplDateCNAR.substring(0,
                                                                       findInitCharPos);
                            } else if (credNoteApplDateCNAR.contains("N/A")) {
                                paymentDescriptionCNAR = "N/A";
                            } else {
                                paymentDescriptionCNAR = "";
                            }


                            processedStatusCNAR = "";
                            errorDetailsCNAR = "";


                            try {
                                pstmtCreditNoteApplicationReportTbl.setString(1,
                                                                              sourceFileName);
                                pstmtCreditNoteApplicationReportTbl.setString(2,
                                                                              credNoteRefNumCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(3,
                                                                              poRefNumCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(4,
                                                                              invoiceNumCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(5,
                                                                              suppNameCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(6,
                                                                              suppIdCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(7,
                                                                              apprvInvoiceAmntCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(8,
                                                                              credNoteTotalAmntCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(9,
                                                                              credNoteApplAmntCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(10,
                                                                              credNoteBalCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(11,
                                                                              apprvInvoiceAdjAmntCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(12,
                                                                              credNoteApplDateCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(13,
                                                                              credNoteUplDateCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(14,
                                                                              rejectedFlagCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(15,
                                                                              releaseHoldFlagCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(16,
                                                                              createInstallmentFlagCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(17,
                                                                              paymentRunFlagCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(17,
                                                                              installmentNumCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(18,
                                                                              paymentDescriptionCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(19,
                                                                              eppReference);
                                pstmtCreditNoteApplicationReportTbl.setString(20,
                                                                              eppCurrency);
                                pstmtCreditNoteApplicationReportTbl.setString(21,
                                                                              processedStatusCNAR);
                                pstmtCreditNoteApplicationReportTbl.setString(22,
                                                                              errorDetailsCNAR);

                                pstmtCreditNoteApplicationReportTbl.execute();

                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }
            }

            sheetIndex++;
        }
        try {
            stmtFetchPaymentDescriptionAIR = conn.createStatement();

            stmtUpdatePaymentDescriptionAIR = conn.createStatement();

            qryFetchPaymentDescriptionAIR =
                    "select  'FC_' || TO_CHAR(TO_DATE(cnar.CRED_NOTE_APPL_DATE,'dd/mm/yyyy'),'YYMMDD') PAY_DESC, " +
                "air.INVOICE_NUM INV_NUM, air.CRED_NOTE_REF_NUM CREDN_REF_NUM from user_1.APPROVED_INVOICES_REPORT air, " +
                "user_1.CREDIT_NOTE_APPL_REPORT cnar WHERE air.INVOICE_NUM = cnar.INVOICE_NUM " +
                "AND air.CRED_NOTE_REF_NUM = cnar.CRED_NOTE_REF_NUM AND " +
                "(air.BPRN_DRN_REF IN ('N/A') OR air.BPRN_DRN_REF IS NULL)"; 

            ResultSet rsFetchPaymentDescriptionAIR = stmtFetchPaymentDescriptionAIR.executeQuery(qryFetchPaymentDescriptionAIR);

            while (rsFetchPaymentDescriptionAIR != null &&
                   rsFetchPaymentDescriptionAIR.next()) {
                paymentDescriptionAIR = rsFetchPaymentDescriptionAIR.getString(1) != null ? rsFetchPaymentDescriptionAIR.getString(1) : "";
                System.out.println("###### "+paymentDescriptionAIR);
                invoiceNumAIR = rsFetchPaymentDescriptionAIR.getString(2);
                credNoteRefNumAIR = rsFetchPaymentDescriptionAIR.getString(3);

                qryUpdatePaymentDescriptionAIR = "UPDATE user_1.APPROVED_INVOICES_REPORT SET PAYMENT_DESCRIPTION = '" +
                        paymentDescriptionAIR + "' WHERE INVOICE_NUM = '" +
                        invoiceNumAIR + "' AND CRED_NOTE_REF_NUM = '" +
                        credNoteRefNumAIR + "'";

                stmtUpdatePaymentDescriptionAIR.executeQuery(qryUpdatePaymentDescriptionAIR);

            }

            conn.commit();
            pstmtInsertBuyerPreMatReconcReportTbl.close();
            pstmtApprovedInvoicesReportTbl.close();
            pstmtCreditNoteApplicationReportTbl.close();

            workbook.close();

            conn.close();

        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
