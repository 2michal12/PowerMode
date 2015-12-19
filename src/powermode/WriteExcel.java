package powermode;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteExcel {

    private WritableCellFormat times;
    private WritableCellFormat timesBoldUnderline;

    public WriteExcel(String fileName, double[] arr, int size) throws IOException, WriteException {
        File file = new File(fileName);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Uloha3", 0);
        WritableSheet excelSheet = workbook.getSheet(0);

        createLabel(excelSheet);
        createContent(excelSheet, arr, size);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet) throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont timesBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(timesBold);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(false);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers
        addCaption(sheet, 0, 0, "index");
        addCaption(sheet, 1, 0, "time (ns)");
    }

    private void createContent(WritableSheet sheet, double[] arr, int size) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 0; i < size; i++) {
            // First column
            addNumber(sheet, 0, i+1, i);
            // Second column
            addNumber(sheet, 1, i+1, arr[i]);
        }
        // average time
        size++;
        addLabel(sheet, 3, 1, "Average time (ns): ");
        String average = "AVERAGE(B2:B"+size+")";
        sheet.addCell(new Formula(4, 1, average));
        
        //variance
        addLabel(sheet, 3, 2, "Variance: ");
        String variance = "VAR(B2:B"+size+")";
        sheet.addCell(new Formula(4, 2, variance));
        
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s) throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row, double doubl) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, doubl, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }
}
