package LikesAdd;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LikeAddTest {

    public static final String ACCESS_TOKEN = "baffe176851b18fbdc6e82b1aa9de7ca51ef49cb0d3fb6880ec195e5cb75b91b748cb6ee5662e26473c6d";
    public static final String GROUP_ID = "-203027909";
    public static final String TYPE_OF_DATA = "post";
    public static final String API_VERSION = "5.130";
    String postIdValue;

    @BeforeMethod
    void setUp() {

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
                        .formParam("owner_id",GROUP_ID)
                        .formParam("message","Autotest_VK_LikeAddTest")
                        .formParam("signed",1)
                        .post("/wall.post")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.post_id",notNullValue())
                        .extract()
                        .response();
        postIdValue = response.then().extract().jsonPath().getString("response.post_id");
    }

    @Test (priority = 1)
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
                        .formParam("v", API_VERSION)
                        .formParam("type", TYPE_OF_DATA)
                        .formParam("owner_id",GROUP_ID)
                        .formParam("item_id",postIdValue)
                        .post("/likes.add")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response.likes",equalTo(1))
                        .extract()
                        .response();
        response.getBody().print();
        System.out.println("Успешно поставили лайк к созданной записи № " + postIdValue);
    }

    @AfterTest
    void tearDown()
    {
        System.out.println("Удаляем запись № " + postIdValue);

        Response response =
                given()
                        .log().all()
                        .when()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("access_token", ACCESS_TOKEN)
                        .formParam("v", API_VERSION)
                        .formParam("type", TYPE_OF_DATA)
                        .formParam("owner_id",GROUP_ID)
                        .formParam("post_id",postIdValue)
                        .post("/wall.delete")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("response",equalTo(1))
                        .extract()
                        .response();
        response.getBody().print();
    }
}
