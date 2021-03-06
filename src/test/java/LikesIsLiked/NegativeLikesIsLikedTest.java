package LikesIsLiked;

import Credentials.CredentialsValues;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NegativeLikesIsLikedTest implements CredentialsValues{

    public static final String FOREIGN_ITEM_ID = "abcd";

    @Test (priority = 1)
    void negativeLikesIsLikedTest() {

        RestAssured.baseURI = "https://api.vk.com";
        RestAssured.basePath = "/method";
        RestAssured.urlEncodingEnabled = true;
        Response response =
                given()
                        .log().all()
                        .when()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("access_token", ACCESS_TOKEN)
                        .formParam("v", API_VERSION)
                        .formParam("type", TYPE_OF_DATA)
                        .formParam("owner_id",GROUP_ID)
                        .formParam("item_id",FOREIGN_ITEM_ID)
                        .post("/likes.isLiked")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("error.error_msg",equalTo("One of the parameters specified was missing or invalid: item_id not integer"))
                        .body("error.error_code",equalTo(100))

                        .body("error.request_params[0].key",equalTo("method"))
                        .body("error.request_params[0].value",equalTo("likes.isLiked"))

                        .body("error.request_params[1].key",equalTo("oauth"))
                        .body("error.request_params[1].value",equalTo("1"))

                        .body("error.request_params[2].key",equalTo("v"))
                        .body("error.request_params[2].value",equalTo(API_VERSION))

                        .body("error.request_params[3].key",equalTo("type"))
                        .body("error.request_params[3].value",equalTo(TYPE_OF_DATA))

                        .body("error.request_params[4].key",equalTo("owner_id"))
                        .body("error.request_params[4].value",equalTo(GROUP_ID))

                        .body("error.request_params[5].key",equalTo("item_id"))
                        .body("error.request_params[5].value",equalTo(FOREIGN_ITEM_ID))
                        .extract()
                        .response();
        response.getBody().print();
    }

    @AfterTest
    void tearDown()
    {
        System.out.println("Завершаем тест.");
    }
}
