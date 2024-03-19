package in.reqres.tests;

import in.reqres.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.ReqresSpecs.requestSpec;
import static in.reqres.specs.ReqresSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresInTests extends TestBase {
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

    @Test
    @DisplayName("Display users list")
    void displayOfUsersList() {

        UsersListResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .when()
                        .get("/users?page=1")
                        .then()
                        .body(matchesJsonSchemaInClasspath("schemas/success-users-list-schema.json"))
                        .spec(responseSpec(200))
                        .extract().as(UsersListResponseModel.class));

        step("Check response", () ->
                assertThat(response.getPage()).isEqualTo(1));
    }

    @Test
    @DisplayName("Display users")
    void displayOfUser() {

        UserDataResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .when()
                        .get("/users/1")
                        .then()
                        .spec(responseSpec(200))
                        .body(matchesJsonSchemaInClasspath("schemas/success-single-user-schema.json"))
                        .extract().as(UserDataResponseModel.class));

        step("Check response", () -> {
            assertThat(response.getUser().getId()).isEqualTo(1);
            assertThat(response.getUser().getEmail()).isEqualTo("george.bluth@reqres.in");
            assertThat(response.getUser().getFirstName()).isEqualTo("George");
            assertThat(response.getUser().getLastName()).isEqualTo("Bluth");
        });
    }
}
