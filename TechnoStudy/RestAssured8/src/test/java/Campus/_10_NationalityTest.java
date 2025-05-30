package Campus;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class _10_NationalityTest extends CampusParent{
    String NationalityID = "";


    @Test
    public void CreateNationality()
    {
        String natName=randomUreteci.nation().nationality()+ randomUreteci.number().digits(5);

        Map<String, String> newNat=new HashMap<>();
        newNat.put("name",natName);

        NationalityID=
                given()
                        .spec(reqSpec)
                        .body(newNat)

                        .when()
                        .post("/school-service/api/nationality")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;

        System.out.println("NationalityID = " + NationalityID);
    }

    @Test(dependsOnMethods = "CreateNationality")
    public void UpdateNationality()
    {
        Map<String, String> updNat=new HashMap<>();
        updNat.put("id",NationalityID);
        updNat.put("name","İsmet nat"+ randomUreteci.number().digits(5));

        given()
                .spec(reqSpec)
                .body(updNat)

                .when()
                .put("/school-service/api/nationality")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "UpdateNationality")
    public void DeleteNationality()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/nationality/"+NationalityID)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "DeleteNationality")
    public void DeleteNegativeNationality()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/nationality/"+NationalityID)

                .then()
                .log().body()
                .statusCode(400)
        ;
    }



}
