import base.ProjectCreationPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ProjectCreationTest extends BaseTest{
    String projectName ="Sample8";
    String ProjectKey="S8";
    String ProjectDescription="SS_creationkju";
    private static final Logger log = LogManager.getLogger(ProjectCreationTest.class);

    @Test(enabled = true,invocationCount = 1,groups="Sanity",retryAnalyzer = utils.RetryAnalyzer.class,dataProvider = "excelData")
    public void projectCreation(Map<String, String> map) throws InterruptedException {
      String colName =  map.get("pass");
       log.info(colName);
        ProjectCreationPage page = new ProjectCreationPage(driver);
         List<String> repoNames = page.projectCreation(projectName,ProjectKey,ProjectDescription);
       boolean validateRepo = repoNames.contains(projectName);
       if(validateRepo){
           log.info("Repo is Present");
       }else{
           log.info("Repo is  Not Present");
       }

    }
    @Test(enabled = true,invocationCount = 1,groups="Regression",retryAnalyzer = utils.RetryAnalyzer.class)
    public void deleteProject() throws InterruptedException {
        ProjectCreationPage page = new ProjectCreationPage(driver);
        page.deleteProject("Sample1");
        page.deleteProject("Sample2");
        page.deleteProject("Sample3");
        page.deleteProject("Sample5");
        page.deleteProject("Sample8");
    }

}
