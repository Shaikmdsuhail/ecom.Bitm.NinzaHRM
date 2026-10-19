package ecom.Bitm.NinzaHRM.dataProvider;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import ecom.Bitm.NinzaHRM.genericUtility.ExcelUtility;

public class TestDataProvider {

    // LoginData columns: username, password, expectedResult.
    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        return ExcelUtility.getExcelData("LoginData");
    }

    // For tests whose method signature accepts only username and password.
    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() throws IOException {
        Object[][] rows = ExcelUtility.getExcelData("LoginData");
        Object[][] credentials = new Object[rows.length][2];

        for (int i = 0; i < rows.length; i++) {
            if (rows[i].length < 2) {
                throw new IllegalArgumentException(
                    "LoginData must have username and password columns."
                );
            }
            credentials[i][0] = rows[i][0];
            credentials[i][1] = rows[i][1];
        }

        return credentials;
    }

    // ProjectData columns: projectName, managerName, status.
    @DataProvider(name = "projectData")
    public Object[][] projectData() throws IOException {
        return ExcelUtility.getExcelData("ProjectData");
    }
}
