package in.reqres.tests;

import in.reqres.models.LoginBodyModel;
import in.reqres.models.LoginResponseErrorModel;
import in.reqres.models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.ReqresSpecs.requestSpec;
import static in.reqres.specs.ReqresSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthTests extends TestBase{
    @Test
    @DisplayName("Successful login")
    void login() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .contentType(JSON)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(200))
                        .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
                        .extract().as(LoginResponseModel.class));

        step("Check response", () ->
                assertThat(response.getToken()).isNotNull());
    }

    @Test
    @DisplayName("Unsuccessful login with a missing Content Type")
    void loginWithNoContentType() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseErrorModel response = step("Make request", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertThat(response.getError()).isEqualTo("Missing email or username"));
    }

    @Test
    @DisplayName("Unsuccessful login with a missing password")
    void loginWithNoPassword() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");

        LoginResponseErrorModel response = step("Make request", () ->
                given(requestSpec)
                        .contentType(JSON)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }
}
