package TechnoStudy.RESTASSUREDPROJECTPOSTMAN.src.test.java;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;


public class TelmansTask extends Parent {

    List<HashMap<String, Object>> movieList = new ArrayList<>();

    @Test
    public void SearchMovie() {
        List<HashMap<String, Object>> results  =
                given()
                        .spec(rs)
                        .queryParam("api_key", "6ac08ee815054b420518976ada9f2b5f")
                        .queryParam("query", "3 idiots")

                        .when()
                        .get("https://api.themoviedb.org/3/search/multi")


                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().path("results");

        for (HashMap<String, Object> result:results){
            if (result.containsKey("media_type")&& result.containsKey("id")){
                movieList.add(result);
            }
        }

    }

    @Test(dependsOnMethods = "SearchMovie")
    public void GetMovieDetailsByID() {
        for ( HashMap<String, Object> result: movieList) {
            Integer id=(Integer) result.get("id");
            String mediaType=(String) result.get("media_type");
            String endPoint="https://api.themoviedb.org/3/"+mediaType+"/"+id;

            String nameOrTitle=
            given()
                    .spec(rs)
                    .queryParam("api_key", "6ac08ee815054b420518976ada9f2b5f")


                    .when()
                    .get(endPoint)


                    .then()
                    .statusCode(200)
                    .extract().path(mediaType.equals("person") ? "name" : "title");

            System.out.println("Type: " + mediaType + " | Name/Title: " + nameOrTitle + " | ID: " + id);


        }
    }

    @Test
    public void InvalidLogin() {
        HashMap<String, String> invalidLoginData = new HashMap<>();
        invalidLoginData.put("username", "telman11");
        invalidLoginData.put("password", "ximes9DF5_!7Rm");

        given()
                .spec(rs)
                .body(invalidLoginData)


                .when()
                .post("https://0692f8bdd7b5.edge.sdk.awswaf.com/0692f8bdd7b5/994f483f0d69/telemetry")

                .then()
                .statusCode(400)
                .log().body();
    }
}
