


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APISample extends BaseAPI {

    @Test
    public void getUserTest() {

        setBaseUri("https://reqres.in");

        Response response = sendRequest("GET", "/api/users/2", null);

        System.out.println(response.asPrettyString());

       // Assert.assertEquals(response.getStatusCode(), 200);
    }
}
