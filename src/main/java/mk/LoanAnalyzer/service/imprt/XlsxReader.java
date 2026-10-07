package mk.LoanAnalyzer.service.imprt;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class XlsxReader {
    private XSSFWorkbook workbook;
    private int rowIndex;
    private static final DecimalFormat decimalFormatter;
    static {
        // Amounts are stored in form 200,00 which can be parsed with following setup:
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');

        decimalFormatter = new DecimalFormat("#.####", symbols);
    }

    public XlsxReader(File file, int startRowIndex) {
        try {
            workbook = new XSSFWorkbook(file);
            rowIndex = startRowIndex;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (InvalidFormatException e) {
            throw new IllegalStateException(e);
        }
    }

    public XlsxReader(InputStream stream, int initialRowIndex) {
        try {
            workbook = new XSSFWorkbook(stream);
            rowIndex = initialRowIndex;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String[] readLine() {
        XSSFRow row = workbook.getSheetAt(0).getRow(rowIndex++);
        if (row == null) {
            return null;
        }
        List<String> values = new ArrayList<>();
        for (int i=0; i<row.getLastCellNum(); i++) {
            XSSFCell cell = row.getCell(i);
            values.add(cell != null ? formatCell(cell) : null);
        }
        return values.toArray(new String[0]);
    }

    protected String formatCell(XSSFCell cell) {
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {

            double value = cell.getNumericCellValue();

            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                // Date time values are stored as numnbers with special formatting.
                // To be able get the time from it, i need return the whole decimal part.
                return Double.toString(value);
            }
            return decimalFormatter.format(value);
        } else {
            return cell.toString();
        }
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void close() {
        if (workbook == null) {
            return;
        }
        try {
            workbook.close();
            workbook = null;
            Runtime.getRuntime().gc(); // prevent memory leake
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
