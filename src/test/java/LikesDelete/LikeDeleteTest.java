package LikesDelete;

import Credentials.CredentialsValues;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LikeDeleteTest implements CredentialsValues {

    String postIdValue;

    @BeforeMethod
    void setUp() {
        //cоздаем запись и ставим лайк
        RestAssured.baseURI = "https://api.vk.com";
        RestAssured.basePath = "/method";
        RestAssured.urlEncodingEnabled = true;

        Response responsePostAdd =
                given()
                        .log().all()
                        .when()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("access_token", ACCESS_TOKEN)
                        .formParam("v", API_VERSION)
                        .formParam("owner_id", GROUP_ID)
                        .formParam("message", "Autotest_VK")
                        .formParam("signed", 1)
                        .post("/wall.post")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.post_id", notNullValue())
                        .extract()
                        .response();
        postIdValue = responsePostAdd.then().extract().jsonPath().getString("response.post_id");

        Response responseLikeAdd =
                given()
                        .log().all()
                        .when()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("access_token", ACCESS_TOKEN)
                        .formParam("v", API_VERSION)
                        .formParam("type", TYPE_OF_DATA)
                        .formParam("owner_id", GROUP_ID)
                        .formParam("item_id", postIdValue)
                        .post("/likes.add")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.likes", equalTo(1))
                        .extract()
                        .response();
        responseLikeAdd.getBody().print();
    }

    @Test (priority = 1)
    void likesDeleteTest() {

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
                        .formParam("item_id",postIdValue)
                        .post("/likes.delete")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.likes",equalTo(0))
                        .extract()
                        .response();
        response.getBody().print();
    }

    @AfterTest
    void tearDown()
    {
        System.out.println("Завершаем тест. Успешно поставили лайк к созданной записи № " + postIdValue);
    }
}

