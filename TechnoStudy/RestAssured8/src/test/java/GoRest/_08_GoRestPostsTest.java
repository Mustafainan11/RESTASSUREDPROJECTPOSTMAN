package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _08_GoRestPostsTest {
    RequestSpecification reqSpec;
    Faker randomUreteci = new Faker();
    int PostID = 0;

    @BeforeClass
    public void Setup() // başlangıç işlemleri
    {
        // token ve başlangıç set ayarları için spec oluşturuluyor
        reqSpec = new RequestSpecBuilder()   // istek paketi setlenmesi
                .setContentType(ContentType.JSON)  // giden body cinsi
                .addHeader("Authorization", "Bearer b0584a1f04f631e85b563bc4e771ecb462f6e3e03e703e5db846c3ded71ceec6")
                .log(LogDetail.URI)  // log.uri
                .build();
    }


    @Test
    public void CreatePost() {

        String rndTitle= randomUreteci.lorem().sentence();
        String rndParagraph= randomUreteci.lorem().paragraph();

        Map<String, String> newPost = new HashMap<>();
        newPost.put("user_id", "7913849");
        newPost.put("title", rndTitle);
        newPost.put("body", rndParagraph);

        PostID=
                given()
                        .spec(reqSpec)
                        .body(newPost)

                        .when()
                        .post("https://gorest.co.in/public/v2/posts")

                        .then()
                        .statusCode(201)
                        .log().body()
                        .extract().path("id")
        ;

        System.out.println("newPost = " + newPost);
    }

    @Test(dependsOnMethods = "CreatePost")
    public void GetPostById() {

        given()
                .spec(reqSpec)

                .when()
                .get("https://gorest.co.in/public/v2/posts/"+PostID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(PostID))
        ;
    }

    @Test(dependsOnMethods = "GetPostById")
    public void UpdatePost() {

        Map<String, String> newPost = new HashMap<>();
        newPost.put("title", "Yeni Title");

        given()
                .spec(reqSpec)

                .when()
                .put("https://gorest.co.in/public/v2/posts/"+PostID)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "UpdatePost")
    public void DeletePost() {

        given()
                .spec(reqSpec)

                .when()
                .delete("https://gorest.co.in/public/v2/posts/"+PostID)

                .then()
                .statusCode(204)
        ;
    }

    @Test( dependsOnMethods = "DeletePost")
    public void DeletePostNegative() {
        given()
                .spec(reqSpec)

                .when()
                .delete("https://gorest.co.in/public/v2/posts/"+PostID)

                .then()
                .statusCode(404)
        ;
    }


}
