package apitests.day4;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class hrApiWithPath {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }
    @Test
    public void getCountriesWithPath(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);

        //print limit value
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        System.out.println("response.path(\"hasMore\").toString() = " + response.path("hasMore").toString());

        String firstCountryId = response.path("items.country_id[0]");
        System.out.println("firstCountryId = " + firstCountryId);

        String secondCountryName = response.path("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        String link2 =response.path("items.links[2].href[0]");
        System.out.println("link2 = " + link2);

        //get all countries
        List<String> countryNames = response.path("items.country_name");
        System.out.println("countryNames = " + countryNames);

        // assert that all regions ids are equal to 2
        List<Integer>regionIds= response.path("items.region_id");
        for (Integer regionId : regionIds) {
            assertTrue(regionId==2);
        }



    }
    // makes sure we have only IT_PROG as ajob_id
    @Test
    public void test(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"IT_PROG\"}")
                .when().get("/employees");

        List<String> jop_ids = response.path("items.job_id");
        for (String jop_id : jop_ids) {
            assertEquals(jop_id,"IT_PROG");
        }



    }
}








