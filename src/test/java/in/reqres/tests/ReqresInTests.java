package in.reqres.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class ReqresInTests extends TestBase {

    @Test
    void successfulLoginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("schemas/success-login-schema.json"))
                .extract().response();

        assertThat(response.statusCode(), is(200));
        assertThat(response.path("token"), is(notNullValue()));
    }

    @Test
    void noContentTypeTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = given()
                .log().uri()
                .log().method()
                .log().body()
                .body(body)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .extract().response();

        assertThat(response.statusCode(), is(400));
        assertThat(response.path("error"), is("Missing email or username"));
    }

    @Test
    void successfulDisplayOfUsersList() {

        Response response = given()
                .log().uri()
                .log().method()
                .when()
                .get("/users?page=1")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("schemas/success-users-list-schema.json"))
                .extract().response();

        assertThat(response.statusCode(), is(200));
        assertThat(response.path("page"), is(1));
        assertThat(response.path("per_page"), is(6));
    }

    @Test
    void successfulDisplayOfSingleUser() {

        Response response = given()
                .log().uri()
                .log().method()
                .when()
                .get("/users/1")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("schemas/success-single-user-schema.json"))
                .extract().response();

        assertThat(response.statusCode(), is(200));
        assertThat(response.path("data.id"), is(1));
        assertThat(response.path("data.email"), is("george.bluth@reqres.in"));
        assertThat(response.path("data.first_name"), is("George"));
        assertThat(response.path("data.last_name"), is("Bluth"));
    }

    @Test
    void unsuccessfulDisplayOfZeroIdUser() {

        Response response = given()
                .log().uri()
                .log().method()
                .when()
                .get("/users/0")
                .then()
                .log().status()
                .log().body()
                .extract().response();

        assertThat(response.statusCode(), is(404));
    }

}
