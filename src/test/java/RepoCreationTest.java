import pages.RepoCreationPage;
import org.testng.annotations.Test;

public class RepoCreationTest extends BaseTest{

    @Test(enabled = true,invocationCount = 1,groups="Regression",retryAnalyzer = utils.RetryAnalyzer.class)
    public void RepoCreation() throws InterruptedException {
        RepoCreationPage page  = new RepoCreationPage(driver);
        page.createRepo("RepoName","Repo","Yes","develop");

    }
}


