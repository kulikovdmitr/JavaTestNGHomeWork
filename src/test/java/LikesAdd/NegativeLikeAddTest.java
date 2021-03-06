package LikesAdd;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NegativeLikeAddTest {

    public static final String ACCESS_TOKEN = "baffe176851b18fbdc6e82b1aa9de7ca51ef49cb0d3fb6880ec195e5cb75b91b748cb6ee5662e26473c6d";

    @DataProvider(name = "test-data")
    public Object[] createTestData() {
        return new String[][] {
                {"likes.add", "5.130", "post", "", "22"}
        };
    }

    @Test(priority = 2, dataProvider = "test-data")
    void likesAddTest(String methodName, String versionApi, String typeData, String ownerId, String itemId) {

        RestAssured.baseURI = "https://api.vk.com";
        RestAssured.basePath = "/method";
        RestAssured.urlEncodingEnabled = true;

        Response response =
                given()
                        .log().all()
                        .when()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("access_token", ACCESS_TOKEN)
                        .formParam("v", versionApi)
                        .formParam("type", typeData)
                        .formParam("owner_id",ownerId)
                        .formParam("item_id",itemId)
                        .post("/"+ methodName)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("error.error_msg",equalTo("One of the parameters specified was missing or invalid: object not found"))
                        .body("error.error_code",equalTo(100))

                        .body("error.request_params[0].key",equalTo("method"))
                        .body("error.request_params[0].value",equalTo(methodName))

                        .body("error.request_params[1].key",equalTo("oauth"))
                        .body("error.request_params[1].value",equalTo("1"))

                        .body("error.request_params[2].key",equalTo("v"))
                        .body("error.request_params[2].value",equalTo(versionApi))

                        .body("error.request_params[3].key",equalTo("type"))
                        .body("error.request_params[3].value",equalTo(typeData))

                        .body("error.request_params[4].key",equalTo("owner_id"))
                        .body("error.request_params[4].value",equalTo(ownerId))

                        .body("error.request_params[5].key",equalTo("item_id"))
                        .body("error.request_params[5].value",equalTo(itemId))
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
