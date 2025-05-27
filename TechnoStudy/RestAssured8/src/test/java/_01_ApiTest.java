
 import io.restassured.http.ContentType;
 import org.testng.annotations.Test;

 import static io.restassured.RestAssured.*;
 import static org.hamcrest.Matchers.*;
    public class _01_ApiTest {
        @Test
        public void Test1() {
            // 1- Endpoint i çağırmadna önce hazırlıkların yapıldığı bölüm : Request, gidecek body, token
            // 2- Endpoint in çağrıldığı bölüm  : Endpoint in çağrılması(METOD: GET,POST ..)
            // 3- Endpoint çağrıldıktan sonraki bölüm : Response, Test(Assert), data

            given().
                    //1.bölümlerle ilgili işler : giden body,token
                            when().
                    //2.bölümlerle ilgili işler : gidiş metodu , endpoint, apinin çağrılma kısmı
                            then()
            //3.bölümlerle ilgili işler: gelen data, assert,test
            ;
        }

        @Test
        public void statusCodeTest() {
            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()  // dönüş datasını gösterir  all: bütün bilgiler
                    .statusCode(200) // dönen değer 200 e eşitmi, assert
            ;
        }

        @Test
        public void contentTypeTest() {
            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body()  // dönen body yi yaz
                    .statusCode(200)  // donen status code 200 MÜ assert
                    .contentType(ContentType.JSON) // donen içerik formatı JSON MI assert
            ;
        }

        @Test
        public void checkCountryInResponseBody() {
            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .log().body() // dönüş datalarını yaz / all: tüm dönüş bilgilerini yaz
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("country", equalTo("United States"))  // assert//country kısmında  United States var mı
            // country yi dışarı almadan
            // bulundu yeri (path i) vererek içerde assertion yapıyorum.
            // Bunu hamcrest kütüphanesi yapıyor
            ;

        }

        @Test
        public void checkHasItem() {
            // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
            // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
            // olduğunu doğrulayınız,

            given()

                    .when()
                    .get("http://api.zippopotam.us/tr/01000")

                    .then()
                    //.log().body()
                    .body("places.'place name'", hasItem("Dörtağaç Köyü"));
            //places içindeki bütün place name ler in
            // içinde Dörtağaç Köyü var MI (assert)

        }

        @Test
        public void bodyArrayHasSizeTest() {
            // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
            // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .body("places", hasSize(1))
            ;
        }

        @Test
        public void combiningTest() {

            given()

                    .when()
                    .get("http://api.zippopotam.us/us/90210")

                    .then()
                    .statusCode(200) // assert
                    .contentType(ContentType.JSON)  // assert
                    .body("places", hasSize(1)) // assert
                    .body("places.'place name'", hasItem("Beverly Hills")) // assert
                    .body("country", equalTo("United States")) // assert
            ;

        }

        @Test
        public void pathParamTest() {

            given()
                    .pathParam("ulke", "us")  // değişkenler hazırlandı
                    .pathParam("pk", 90210)
                    .log().uri()   // oluşacak endpoint i yazdıralım

                    .when()
                    .get("http://api.zippopotam.us/{ulke}/{pk}")

                    .then()
                    .log().body()
            ;
        }

        @Test
        public void queryParamTest() {
            //https://gorest.co.in/public/v1/users?page=3

            given()
                    .param("page", 3)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
            ;
        }

        @Test
        public void queryParamTest2() {
            // https://gorest.co.in/public/v1/users?page=3
            // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki
            // donen page degerlerinin çağrılan page nosu ile aynı
            // olup olmadığını kontrol ediniz.

            for (int p = 1; p <= 10; p++) {

                given()
                        .param("page", p)
                        .log().uri()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .body("meta.pagination.page", equalTo(p))
                ;
            }

        }
    }
