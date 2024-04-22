package in.reqres.tests;

import in.reqres.models.UserDataResponseModel;
import in.reqres.models.UsersListResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.ReqresSpecs.requestSpec;
import static in.reqres.specs.ReqresSpecs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTests extends TestBase{
    @Test
    @DisplayName("Users list is displayed")
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
    @DisplayName("A single user is displayed")
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
