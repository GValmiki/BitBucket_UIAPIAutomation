package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.UIUtility;

public class RepoCreationPage {
    WebDriver driver;

    //input[@name="branch_name"]
    //div[@id="id_gitignore_type_group"]//child::a//child::span[1]

    public WebElement projectSelection(String ProjectName){
       return driver.findElement(By.xpath("//div[@role='option']//child::span[text()='"+ProjectName+"']"));
    }
    public WebElement projectReadmeDropSelectSelection(String Readme){
        return driver.findElement(By.xpath("//ul[@id='select2-results-1']//child::li//child::div[text()='"+Readme+"']"));
    }
    @FindBy(xpath = "//button[@id='createGlobalItem']")
    public WebElement createBtn;
    @FindBy(xpath = "//span[text()='Create repository']")
    public WebElement createRepoBtn;
    @FindBy(xpath = "//span[text()='Repository']")
    public WebElement createRepo;

    //span[@id='select2-chosen-5']
    @FindBy(xpath = "(//a[@role='button'])[1]")
    public WebElement repoCreateProjectDropdown;
//    @FindBy(xpath = "//div[@role='option']//child::span[text()='go']")
//    public WebElement projectSelection;
    @FindBy(xpath = "//input[@id='id_name']")
    public WebElement RepoName;
    @FindBy(xpath = "//div[@id='s2id_id_readme_type']")
    public WebElement ReadmeDrop;
//    @FindBy(xpath = "//ul[@id='select2-results-1']//child::li//child::div[text()='No']")
//    public WebElement ReadmeDropSelect;
    @FindBy(xpath = "//input[@name='branch_name']")
    public WebElement branchName;
    @FindBy(xpath = "//button[text()='Create repository']")
    public WebElement submitRepo;
    @FindBy(xpath = "//span[contains(text(),'put some bits in your bucket')]")
    public WebElement SuccessBitbucketMessage;
    public RepoCreationPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public boolean createRepo(String SelectProject,String Reponame,String IncludeReadme,String DefaultBranchName) throws InterruptedException {
        UIUtility utility = new UIUtility(driver);
        utility.waitAndClick(createBtn);
        utility.waitAndClick(createRepo);
        Thread.sleep(5000);
        utility.waitAndClick(repoCreateProjectDropdown);
        Thread.sleep(5000);
        utility.waitAndClick(projectSelection(SelectProject));
       utility.waitAndSendKeys(RepoName,Reponame);
        utility.waitAndClick(ReadmeDrop);
        utility.waitAndClick(projectReadmeDropSelectSelection(IncludeReadme));
        utility.waitAndSendKeys(branchName,DefaultBranchName);
        utility.waitAndClick(submitRepo);
        String actual = utility.waitAndGetText(SuccessBitbucketMessage);
      boolean compare =   actual.contains("put some bits in your bucket')]") ;
      return  compare;

    }









}
