package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CampusParent {
    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;

    @BeforeClass
    public void Setup() {
        baseURI ="https://test.mersys.io"; // RestAsured un kendi değişkeni

        // login ol , token al, spec i hazırla
        Map<String, String> credential = new HashMap<>();
        credential.put("username", "Campus25");
        credential.put("password", "Campus.2524");
        credential.put("rememberMe", "true");

        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("/auth/login")// eğer http ile başlamıyorsa baseURI başa otomatik eklenir.

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("access_token");

        System.out.println("token = " + token);

        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
