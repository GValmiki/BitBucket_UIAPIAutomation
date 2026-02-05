package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.UIUtility;

import java.util.ArrayList;
import java.util.List;

public class ProjectCreationPage {
    WebDriver driver;

    @FindBy(xpath = "//span[text()='Projects']")
    public WebElement projectBtn;
    @FindBy(xpath = "//span[text()='Create Project']")
    public WebElement projectCreateBtn;
    @FindBy(xpath = "//input[@name='name']")
    public WebElement projectName;
    @FindBy(xpath = "//input[@id='id_key']")
    public WebElement projectKey;
    @FindBy(xpath = "//textarea[@name='description']")
    public WebElement projectDescription;
    @FindBy(xpath = "//label[@for='id_is_private']")
    public WebElement projectPrivateCheckBox;   // checkbox by default private
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement projectSubmitBtn;
    @FindBy(xpath = "//a[@class='cancel aui-button aui-button-link']")
    public WebElement projectcancelBtn;
    @FindBy(xpath = "//span[text()='Project settings']")
    public WebElement projectSettings;
    @FindBy(xpath = "//div[@aria-label='General']//child::a[1]")
    public WebElement projectDetails;
    @FindBy(xpath = "//div[text()='Delete project']")
    public WebElement deleteProject;
    @FindBy(xpath = "//span[text()='Delete']")
    public WebElement deletePopup;






    public ProjectCreationPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }


    public List<String> projectCreation(String ProjectName, String ProjectKey, String ProjectDescription ) throws InterruptedException {
        List<String> RepoNames = new ArrayList<String>();
        List<WebElement> RepoList = driver.findElements(By.xpath("//table[@class='css-1yq88hf edylmxf13']//child::tbody//child::tr//child::td[1]"));

        UIUtility utility = new UIUtility(driver);
        utility.waitAndClick(projectBtn);
        utility.waitAndClick(projectCreateBtn);
        utility.waitAndSendKeys(projectName,ProjectName);
        utility.clearField(projectKey);
        utility.waitAndSendKeys(projectKey,ProjectKey);
        utility.waitAndSendKeys(projectDescription,ProjectDescription);
        utility.waitAndClick(projectPrivateCheckBox);
        Thread.sleep(8000);
        utility.waitAndClick(projectSubmitBtn);
        Thread.sleep(2000);
        utility.waitAndClick(projectBtn);
        Thread.sleep(2000);
          for(WebElement x:RepoList){
            RepoNames.add(x.getText())  ;
        }
    return RepoNames;
    }

public void deleteProject(String ProjectName) throws InterruptedException {
    UIUtility utility = new UIUtility(driver);
    Thread.sleep(3000);
     utility.waitAndClick(projectBtn);
    Thread.sleep(3000);
    WebElement repoNames = driver.findElement(By.xpath("(//a[text()='"+ProjectName+"'])[1]"));
    utility.waitAndClick(repoNames);
    utility.waitAndClick(projectSettings);
            utility.waitAndClick(projectDetails);
            utility.waitAndClick(deleteProject);
    utility.waitAndClick(deletePopup);
    }




}
