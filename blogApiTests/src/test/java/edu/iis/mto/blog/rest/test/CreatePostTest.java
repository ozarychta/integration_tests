package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class CreatePostTest extends FunctionalTests {

    private static final String POST_API_CONFIRMED_USER = "/blog/user/1/post";
    private static final String POST_API_NEW_USER = "/blog/user/2/post";

    @Test
    public void addPost_confirmedUser_shouldAdd() {
        JSONObject jsonObj = new JSONObject().put("entry", "entry");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(POST_API_CONFIRMED_USER);
    }

    @Test
    public void addPost_notConfirmedUser_shouldNotAdd() {
        JSONObject jsonObj = new JSONObject().put("entry", "entry");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_API_NEW_USER);
    }
}
