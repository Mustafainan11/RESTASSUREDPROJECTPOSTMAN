package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class _09_CountryTest {
    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;
    String CountryID = "";


    @BeforeClass
    public void Setup() {
        // login ol , token al, spec i hazırla
        Map<String, String> credential = new HashMap<>();
        credential.put("username", "Campus25");
        credential.put("password", "Campus.2524");//login
        credential.put("rememberMe", "true");

        String token =   //string oldugu için token
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("https://test.mersys.io/auth/login")//login

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("access_token");//token alıyoruz

        System.out.println("token = " + token);

        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)//kısma tokenı ekliyoruz
                .build();
    }

    @Test
    public void CreateCountry() {
        String ulkeAdi = randomUreteci.address().country() + randomUreteci.number().digits(5);
        String ulkeKodu = randomUreteci.address().countryCode() + randomUreteci.number().digits(5);

        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", ulkeAdi);
        newCountry.put("code", ulkeKodu);

        CountryID =
                given()
                        .spec(reqSpec)
                        .body(newCountry)

                        .when()
                        .post("https://test.mersys.io/school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")//ıd aldık
        ;

        System.out.println("CountryID = " + CountryID);//ıd buraya koyduk
    }
        @Test(dependsOnMethods = "CreateCountry")
        public void UpdateCountry()
        {
            Map<String, String> uptCountry = new HashMap<>();
            uptCountry.put("id", CountryID);
            uptCountry.put("name", "İsmet Ülkesi"+randomUreteci.number().digits(5));
            uptCountry.put("code", "is2323"+randomUreteci.number().digits(5));


            given()
                    .spec(reqSpec)
                    .body(uptCountry)

                    .when()
                    .put("https://test.mersys.io/school-service/api/countries")

                    .then()
                    .log().body()
                    .statusCode(200)
            ;
        }
    @Test(dependsOnMethods = "UpdateCountry")
    public void DeleteCountry() {

        given()
                .spec(reqSpec)

                .when()
                .delete("https://test.mersys.io/school-service/api/countries/"+CountryID)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "DeleteCountry")
    public void DeleteCountryNegative() {
        given()
                .spec(reqSpec)

                .when()
                .delete("https://test.mersys.io/school-service/api/countries/"+CountryID)

                .then()
                .log().body()
                .statusCode(400)
        ;
    }
    // TODO: GetCountryId ->  get("school-service/api/countries/"+CountryID) bir tane country get
    //       AllCountryId ->  get("school-service/api/countries"); id leri list şeklinde alıcaksınız
    //1- GetCountryById yi Create den sonra ya ekleyiniz.
    //2- CreateCountryNegative   Create den sonra ya ekleyiniz.
    //3- Bütün Country leri siliniz. (Günün sorusu)

    @Test
    public void deleteAllCountries()
    {
        // Önce id leri alalım
        List<String> countryIds=
                given()
                        .spec(reqSpec)

                        .when()
                        .get("/school-service/api/countries")

                        .then()
                        //.log().body()
                        .extract().jsonPath().getList("id")
                ;

        System.out.println("countryIds = " + countryIds);

        // Sonra for döngüsü ile teker teker silelim
        for (String id : countryIds){

            Response response=
                    given()
                            .spec(reqSpec)
                            .when()
                            .delete("/school-service/api/countries/"+id)
                            .then()
                            //.log().body()
                            .extract().response();

            if (response.getStatusCode() == 200)
                System.out.println("Başarıyla silindi");
            else
                System.out.println("Silinemedi : "+ response.path("message"));
        }

    }



}

