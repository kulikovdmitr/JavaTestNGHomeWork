package LikesDelete;

import Credentials.CredentialsValues;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.likes.Type;
import com.vk.api.sdk.objects.likes.responses.AddResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class LikeDeleteVkSdkDraftTest implements CredentialsValues {


    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vk = new VkApiClient(transportClient);

    ServiceActor actor = new ServiceActor(APP_ID, CLIENT_SECRET, ACCESS_TOKEN);

    @Test(priority = 1)
    void setUp(UserActor actor) throws ClientException, ApiException {
        AddResponse getResponse =
                vk.likes().add(actor, Type.POST, 1234)
                .ownerId(1)
                .execute();
    }

    @Test(priority = 3)
    void tearDown()
    {
        System.out.println("Завершаем тест");
    }
}
