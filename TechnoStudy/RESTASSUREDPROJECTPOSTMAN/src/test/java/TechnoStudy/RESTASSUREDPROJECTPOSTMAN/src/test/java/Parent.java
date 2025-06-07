package TechnoStudy.RESTASSUREDPROJECTPOSTMAN.src.test.java;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Parent {
    Faker faker = new Faker();
    RequestSpecification rs;


    @BeforeClass
    public void Setup() {

       // baseURI="https://www.themoviedb.org/login";
        HashMap<String, String> loginData = new HashMap<>();
        loginData.put("username", "telman1123");
        loginData.put("password", "ximes9DF5_!7Rmu");

        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body(loginData)

                        .when()
                        .post("https://0692f8bdd7b5.edge.sdk.awswaf.com/0692f8bdd7b5/994f483f0d69/telemetry")


                        .then().log().body()
                        .extract().path("token");

        System.out.println(token);

        rs = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
