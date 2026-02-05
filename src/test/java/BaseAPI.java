

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseAPI {

    protected RequestSpecification request;
    protected Response response;

    public void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
    }

    public Response sendRequest(String method, String endpoint, Object body) {

        request = RestAssured.given()
                .header("Content-Type", "application/json");

        if (body != null) {
            request.body(body);
        }

        switch (method.toUpperCase()) {
            case "GET":
                response = request.get(endpoint);
                break;

            case "POST":
                response = request.post(endpoint);
                break;

            case "PUT":
                response = request.put(endpoint);
                break;

            case "DELETE":
                response = request.delete(endpoint);
                break;
        }

        return response;
    }
    @DataProvider(name = "excelData")
    public Object[][] getData(Method method) {

        Map<String, String> map = new HashMap<>();

        String excelName = method.getDeclaringClass().getSimpleName() + ".xlsx";
        String path = "src/test/resources/testdata/" + excelName;

        try (FileInputStream fis = new FileInputStream(new File(path))) {

            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row.getCell(0).getStringCellValue()
                        .equalsIgnoreCase(method.getName())) {

                    for (int j = 0; j < header.getLastCellNum(); j++) {
                        map.put(
                                header.getCell(j).getStringCellValue(),
                                row.getCell(j).toString()
                        );
                    }
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Excel read failed", e);
        }

        return new Object[][]{{ map }};
    }
}
