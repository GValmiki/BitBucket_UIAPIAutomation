import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.Constants;
import utils.ExtentManager;
import utils.UIUtility;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

class BaseTest {
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    String url = ConfigReader.get("url");
    String username = ConfigReader.get("username");
    String password = ConfigReader.get("password");


    // ---------------- LOCATORS ----------------
    @FindBy(xpath = "//input[@placeholder='Enter your email']")
    public WebElement usernameTxt;

    @FindBy(xpath = "//input[@placeholder='Enter password']")
    public WebElement passwordTxt;

    @FindBy(xpath = "//span[text()='Log in']")
    public WebElement loginBtn;
    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement ContinueBtn;


    @FindBy(xpath = "//span[text()='Welcome to Bitbucket!']")
    public WebElement successMessage;

    // ---------------- DRIVER SETUP ----------------
    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
        driver = initDriver(browser);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // PageFactory init after driver
        PageFactory.initElements(driver, this);
    }

    // ---------------- DRIVER TEARDOWN ----------------
    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    // ---------------- BROWSER INITIALIZATION ----------------
    public WebDriver initDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
        }
        return driver;
    }


    // ---------------- TEST ----------------
    //@BeforeMethod
    @BeforeMethod
    public void loginTest() throws InterruptedException {
        UIUtility utility = new UIUtility(driver);
        driver.get(url);
        utility.waitAndSendKeys(usernameTxt,username);
        utility.waitAndClick(ContinueBtn);
        utility.waitAndSendKeys(passwordTxt,password);
        utility.waitAndClick(loginBtn);
        String message = utility.waitAndGetText(successMessage);
        Assert.assertEquals(message, Constants.WELCOME_MESSAGE,"Success Login");
    }
    @BeforeSuite
    public void startReport() {
        extent = ExtentManager.getExtent();
    }

    @BeforeMethod
    public void createTest(Method method) {
        ExtentTest extentTest =
                extent.createTest(method.getName());
        test.set(extentTest);
    }
    @AfterMethod
    public void updateResultRetry(ITestResult result) {

        Integer retryCount =
                (Integer) result.getAttribute("RETRY_COUNT");

        if (result.getStatus() == ITestResult.FAILURE) {

            if (retryCount != null) {
                test.get().warning(
                        "Test failed – retry attempt: " + retryCount
                );
                log.warn("Retrying test: " + result.getName()
                        + " | Attempt: " + retryCount);
            } else {
                test.get().fail(result.getThrowable());
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Test Passed");
        } else {
            test.get().skip("Test Skipped");
        }
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


    @AfterMethod
    public void updateResult(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Test Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.get().fail(result.getThrowable());
        } else {
            test.get().skip("Test Skipped");
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}
