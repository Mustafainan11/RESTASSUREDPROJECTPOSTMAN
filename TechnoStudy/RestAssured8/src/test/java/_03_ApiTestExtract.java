import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class _03_ApiTestExtract {

    @Test
    public void extractingJsonPath() {

        //var dataUserID=pm.response.json().id;
        String ulke=
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("country") // PATH i country olan değeri EXTRACT yap
                ;

        System.out.println("ulke = " + ulke);
        Assert.assertEquals(ulke, "United States");
    }
    @Test
    public void extractingJsonPath2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız

        String state=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        //.body("places[0].state", equalTo("California"))
                        .extract().path("places[0].state")  // extract en son komut olmalı
                ;

        System.out.println("state = " + state); // kendimize kontrol
        Assert.assertEquals(state,"California"); // assert
    }

    @Test
    public void extractingJsonPath3() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.

        int limit=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .extract().path("meta.pagination.limit")
                ;

        Assert.assertTrue(limit == 10);

    }
    @Test
    public void extractingJsonPath4() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // data daki bütün idlerin içinde 7905915 değerinin geçtiğini
        // TestNg assertion ile doğrulayınız.

        ArrayList<Integer> idler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().path("data.id");

        System.out.println("idler = " + idler);
        Assert.assertTrue(idler.contains(7905915));
    }

    @Test
    public void extractingJsonPath5() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // bütün name leri in içiden "Arnesh Achari" değerinin geçtiğini
        // TestNg assertion ile doğrulayınız.

        ArrayList<String> nameler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().path("data.name");

        System.out.println("nameler = " + nameler);
        Assert.assertTrue(nameler.contains("Arnesh Achari"));
    }
    @Test
    public void extractingJsonPathResponsAll() {

        //sorgudan dönen tüm datayı aldım
        Response donenBody =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().response();

        ArrayList<String> nameler = donenBody.path("data.name");
        ArrayList<Integer> idler = donenBody.path("data.id");
        int limit=  donenBody.path("meta.pagination.limit");

        System.out.println("limit = " + limit);
        System.out.println("idler = " + idler);
        System.out.println("nameler = " + nameler);

        Assert.assertTrue(nameler.contains("Arnesh Achari"));
        Assert.assertTrue(idler.contains(7905915));
        Assert.assertTrue(limit == 10);
    }
}



