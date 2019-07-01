package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class FindUserTest extends FunctionalTests  {

    private static final String USER_API_FIND_USER = "/blog/user/find";

    @Test
    public void findUser_shouldFindTwoUsers() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .param("searchString", "Steward")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size", is(2))
                .when()
                .get(USER_API_FIND_USER);
    }

    @Test
    public void findUser_shouldFindRemovedUser() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .param("searchString", "Removed")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size", is(0))
                .when()
                .get(USER_API_FIND_USER);
    }
}
