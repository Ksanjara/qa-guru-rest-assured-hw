package tests;

import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DeleteUserSpec.deleteRequestSpec;
import static specs.DeleteUserSpec.deleteResponseSpec;
import static specs.RegistrationSpec.*;
import static specs.UpdateUserSpec.updateRequestSpec;
import static specs.UpdateUserSpec.updateResponseSpec;

public class ReqresApiTests {

    @BeforeAll
    public static void preconditionsForAllTests() {
        RestAssured.baseURI = "https://reqres.in";
        //    RestAssured.basePath = "/api";
    }

    @Test
    void successfulRegistrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();

        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () ->
                given(registrationRequestSpec)
                        .body(regData)

                        .when()
                        .post()

                        .then()
                        .spec(registrationResponseSpec)
                        .extract().as(RegistrationResponseModel.class));

        step("Check response", () -> Assertions.assertNotNull(response.getToken()));
    }

    @Test
    void unsuccessfulRegistrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("sydney@fife");

        RegistrationErrorModel regError = step("Make request", () ->
                given(registrationRequestSpec)
                        .body(regData)

                        .when()
                        .post()

                        .then()
                        .spec(unsuccessfulRegistrationResponseSpec)
                        .extract().as(RegistrationErrorModel.class));

        step("Check response", () -> Assertions.assertEquals("Missing password", regError.getError()));
    }

    @Test
    void updatePutTest() {
        UpdateUserBodyModel regData = new UpdateUserBodyModel();
        regData.setName("morpheus");
        regData.setJob("zion resident");

        UpdateUserResponseModel response = step("Make request", () ->
                given(updateRequestSpec)
                        .body(regData)

                        .when()
                        .put()

                        .then()
                        .spec(updateResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));

        step("Check response", () ->
                Assertions.assertTrue(response.getUpdatedAt().startsWith(String.valueOf(LocalDate.now()))));

    }

    @Test
    void updatePatchTest() {
        UpdateUserBodyModel regData = new UpdateUserBodyModel();
        regData.setName("morpheus");
        regData.setJob("zion resident");

        UpdateUserResponseModel response = step("Make request", () ->
                given(updateRequestSpec)
                        .body(regData)

                        .when()
                        .patch()

                        .then()
                        .spec(updateResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));

        step("Check response", () ->
                Assertions.assertTrue(response.getUpdatedAt().startsWith(String.valueOf(LocalDate.now()))));

    }

    @Test
    void deleteUserTest() {
        step("Make request and assert", () ->
                given(deleteRequestSpec)
                        .when()
                        .delete()

                        .then()
                        .spec(deleteResponseSpec)
        );

    }

}




