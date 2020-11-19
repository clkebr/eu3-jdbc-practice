package apitests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class SpartanTestWithParameters {

    @BeforeClass
    public void beforeclass(){
        baseURI="http://54.226.186.24:8000";
    }

    /*
          Given accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json;charset=UTF-8
          And "Blythe" should be in response payload(body)
       */

    @Test
    public void test(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",5)
                .when().get("/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
        Assert.assertTrue(response.body().asString().contains("Blythe"));
    }

    /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json;charset=UTF-8
        And Spartan Not Found" message should be in response payload
     */
    @Test
    public  void test2(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get("/api/spartans/{id}");
        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
        Assert.assertTrue(response.body().asString().contains("Spartan Not Found"));
    }




}
