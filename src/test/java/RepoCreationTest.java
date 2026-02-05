import base.ProjectCreationPage;
import base.RepoCreationPage;
import org.testng.annotations.Test;

import java.util.List;

public class RepoCreationTest extends BaseTest{

    @Test(enabled = true,invocationCount = 1,groups="Regression",retryAnalyzer = utils.RetryAnalyzer.class)
    public void RepoCreation() throws InterruptedException {
        RepoCreationPage page  = new RepoCreationPage(driver);
        page.createRepo("RepoName","Repo","Yes","develop");

    }
}


