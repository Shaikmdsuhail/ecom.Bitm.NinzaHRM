package ecom.Bitm.NinzaHRM.genericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    private static final String EXCEL_PATH =
            "src/test/resources/TestData.xlsx";

    public static Object[][] getExcelData(String sheetName)
            throws IOException {

        Path path = Paths.get(EXCEL_PATH);

        if (!java.nio.file.Files.exists(path)) {
            throw new IOException(
                    "Excel file not found: " + path.toAbsolutePath()
            );
        }

        DataFormatter formatter = new DataFormatter();
        List<Object[]> rows = new ArrayList<>();

        try (FileInputStream fis =
                     new FileInputStream(path.toFile());
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException(
                        "Sheet not found: " + sheetName
                );
            }

            Row header = sheet.getRow(0);

            if (header == null) {
                throw new IOException(
                        "Header row is missing in: " + sheetName
                );
            }

            int columnCount = header.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Object[] data = new Object[columnCount];
                boolean hasData = false;

                for (int j = 0; j < columnCount; j++) {
                    String value = "";

                    if (row.getCell(j) != null) {
                        value = formatter.formatCellValue(
                                row.getCell(j)
                        ).trim();
                    }

                    data[j] = value;

                    if (!value.isEmpty()) {
                        hasData = true;
                    }
                }

                if (hasData) {
                    rows.add(data);
                }
            }
        }

        return rows.toArray(new Object[0][]);
    }
}
