package LikesAdd;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LikeAddTest {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String ACCESS_TOKEN = "";
    String access_token = "";

    @Test(priority = 1)
    void setUp()
    {
        RestAssured.baseURI = "https://oauth.vk.com";
        RestAssured.basePath = "/access_token";

        Response response =
                given()
                        .log().all()
                        .when()
                        .formParam("client_id", CLIENT_ID)
                        .formParam("client_secret", CLIENT_SECRET)
                        .formParam("v", "5.130")
                        .formParam("grant_type","client_credentials")
                        .get("")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("access_token",notNullValue())
                        .extract()
                        .response();
        access_token = response.getBody().path("access_token");
    }

    @Test(priority = 2)
    void likesAddTest() {

        RestAssured.baseURI = "https://api.vk.com";
        RestAssured.basePath = "/method";
        RestAssured.urlEncodingEnabled = true;

        Response response =
                given()
                        .log().all()
                        .when()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("access_token", ACCESS_TOKEN)
                        .formParam("v", "5.130")
                        .formParam("type", "post")
                        .formParam("owner_id","503923867")
                        .formParam("item_id",22)
                        .post("/likes.add")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.likes",equalTo(1))
                        .extract()
                        .response();
        response.getBody().print();
    }

    @Test(priority = 3)
    void tearDown()
    {
        System.out.println("Завершаем тест");
    }
}
