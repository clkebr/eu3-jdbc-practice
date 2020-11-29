package apitests.homework;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class hw1 {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }
    /*
    Q1:
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is
*/

    @Test
    public void q1(){
        Response response = given().contentType(ContentType.JSON).and()
                .queryParam("q", "{\"country_id\": \"US\"}").and()
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath = response.jsonPath();


        assertEquals(jsonPath.getString("items.country_id[0]"),"US");
        assertEquals(jsonPath.getString("items.country_name[0]"),"United States of America");
        assertEquals(jsonPath.getInt("items.region_id[0]"),2);
    }
/*
Q2:
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
*/
    @Test
    public void q2(){
            Response response = given().accept(ContentType.JSON).and()
                    .queryParam("q", "{\"department_id\":80}").and()
                    .when().get("/employees");
            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json");
            JsonPath jsonPath=response.jsonPath();

            //- And all job_ids start with 'SA'
            List<Object> listJop_ids = jsonPath.getList("items.job_id");
            for (Object job_id : listJop_ids) {
                assertTrue(job_id.toString().startsWith("SA"));
            }

            // - And all department_ids are 80
            List<Integer> listDep_ids = jsonPath.getList("items.department_id");
            for (Integer dep_id : listDep_ids) {
                assertTrue(dep_id==80);
            }
            List<Object> element = jsonPath.getList("items");
            assertTrue(element.size()==25);

        }
/*
Q3:
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */

    @Test
    public void q3(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q", "{\"region_id\": 3}").and()
                .given().get("//countries");

        assertEquals(response.statusCode(),200);

        JsonPath jsonPath=response.jsonPath();

        List<Integer> list = jsonPath.getList("items.region_id");
        for (Integer region_id : list) {
            assertTrue(region_id==3);
        }
        List<Object> items = jsonPath.getList("items");
        assertTrue(items.size()==6);

        boolean actualHasMore=jsonPath.getJsonObject("hasMore");
        assertEquals(actualHasMore,false);

        List<String> country_names = jsonPath.getList("items.country_name");
        String[] country= {"Australia","China","India","Japan","Malaysia","Singapore"};
        assertEquals(country_names.toArray(),country);


    }
}
