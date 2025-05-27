import Model.ZipCode;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class _04_ApiTestPOJO {
    @Test
    public void extractJsonAll_POJO()
    {
        ZipCode zipCodeNesnesi=
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        //.log().body()
                        .extract().body().as(ZipCode.class)
                ;     // Tüm body all Location.class (kalıba göre) çevir

        System.out.println("zipCodeNesnesi.getCountry() = " + zipCodeNesnesi.getCountry());
        System.out.println("zipCodeNesnesi.getPlaces() = " + zipCodeNesnesi.getPlaces());

        System.out.println("zipCodeNesnesi = " + zipCodeNesnesi);
    }
}
