package in.reqres.tests;

import in.reqres.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.ReqresSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
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
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(universalResponseSpecWithStatusCode200)
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
                given(loginRequestSpecWithNoContentType)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(loginResponseSpecWithStatusCode400)
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
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(loginResponseSpecWithStatusCode400)
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }

    @Test
    @DisplayName("Display users list")
    void displayOfUsersList() {

        UsersListResponseModel response = step("Make request", () ->
                given(usersListRequestSpec)
                        .when()
                        .get()
                        .then()
                        .spec(universalResponseSpecWithStatusCode200)
                        .body(matchesJsonSchemaInClasspath("schemas/success-users-list-schema.json"))
                        .extract().as(UsersListResponseModel.class));

        step("Check response", () ->
                assertThat(response.getPage()).isEqualTo(1));
    }

    @Test
    @DisplayName("Display users")
    void displayOfUser() {

        UserDataResponseModel response = step("Make request", () ->
                given(usersRequestSpec)
                        .when()
                        .get()
                        .then()
                        .spec(universalResponseSpecWithStatusCode200)
                        .body(matchesJsonSchemaInClasspath("schemas/success-single-user-schema.json"))
                        .extract().as(UserDataResponseModel.class));

        step("Check response", () -> {
            assertThat(response.getData().getId()).isEqualTo(1);
            assertThat(response.getData().getEmail()).isEqualTo("george.bluth@reqres.in");
            assertThat(response.getData().getFirstName()).isEqualTo("George");
            assertThat(response.getData().getLastName()).isEqualTo("Bluth");
        });
    }
}
