package TechnoStudy.RESTASSUREDPROJECTPOSTMAN.src.test.java;



import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TelmansTask extends Parent {

    @Test
    public void Test16(){
        given().spec(rs)


                .when()
                .get("https://www.themoviedb.org/search/trending?query=sev")

                .then().log().body();
    }
}
