package LikesDelete;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LikeDeleteTest {

    public static final String ACCESS_TOKEN = "baffe176851b18fbdc6e82b1aa9de7ca51ef49cb0d3fb6880ec195e5cb75b91b748cb6ee5662e26473c6d";
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
                        .formParam("v", "5.130")
                        .formParam("owner_id", "-203027909")
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
                        .formParam("v", "5.130")
                        .formParam("type", "post")
                        .formParam("owner_id", "-203027909")
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
                        .formParam("v", "5.130")
                        .formParam("type", "post")
                        .formParam("owner_id","-203027909")
                        .formParam("item_id",postIdValue)
                        .post("/likes.delete")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.likes",equalTo(1))
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

