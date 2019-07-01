package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class FindPostTest {

    private static final String POST_API_FIND_POST_CONFIRMED_USER = "/blog/user/1/post";
    private static final String POST_API_FIND_POST_REMOVED_USER = "/blog/user/4/post";

    @Test
    public void findPost_confirmedUser_shouldFindOnePost() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size", is(1))
                .when()
                .get(POST_API_FIND_POST_CONFIRMED_USER);
    }

    @Test
    public void findPost_removedUser_shouldNotFind() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .get(POST_API_FIND_POST_REMOVED_USER);
    }
}
