import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresApiTests {

    @Test
    void successfulRegistrationTest(){
        String regData = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .body(regData)
                .contentType(JSON)

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .statusCode(200)
                .body( "token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegistrationTest(){
        String regData = "{\"email\": \"sydney@fife\"}";

        given()
                .body(regData)
                .contentType(JSON)

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .statusCode(400)
                .body( "error", is("Missing password"));
    }

    @Test
    void updatePutTest(){
        String regData = "{ \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"}";


        given()
                .body(regData)
                .contentType(JSON)

                .when()
                .put("https://reqres.in/api/users/2")

                .then()
                .statusCode(200)
                .body( "updatedAt", startsWith(String.valueOf(LocalDate.now())));

    }

    @Test
    void updatePatchTest(){
        String regData = "{ \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"}";

        given()
                .body(regData)
                .contentType(JSON)

                .when()
                .patch("https://reqres.in/api/users/2")

                .then()
                .statusCode(200)
                .body( "updatedAt", startsWith(String.valueOf(LocalDate.now())));

    }

    @Test
    void deleteUserTest(){
                when()
                .delete("https://reqres.in/api/users/2")

                .then()
                .statusCode(204)
                .body(isEmptyString());

    }

}




