package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class CreateLikeTest extends FunctionalTests {

    private static final String LIKE_API_CONFIRMED_USER_NOT_HIS_POST = "/blog/user/1/like/2";
    private static final String LIKE_API_CONFIRMED_USER_NOT_HIS_POST2 = "/blog/user/3/like/1";
    private static final String LIKE_API_CONFIRMED_USER_HIS_POST = "/blog/user/1/like/1";
    private static final String LIKE_API_NEW_USER = "/blog/user/2/like/1";

    @Test
    public void addLike_confirmedUser_postPostedByDifferentUser_shouldAdd() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(LIKE_API_CONFIRMED_USER_NOT_HIS_POST);
    }

    @Test
    public void addLike_confirmedUser_postPostedByThatUser_shouldNotAdd() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(LIKE_API_CONFIRMED_USER_HIS_POST);
    }

    @Test
    public void addLike_notConfirmedUser_shouldNotAdd() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(LIKE_API_NEW_USER);
    }

    @Test
    public void addLike_secondLike_doesNotChangeAnything() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(LIKE_API_CONFIRMED_USER_NOT_HIS_POST2);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(LIKE_API_CONFIRMED_USER_NOT_HIS_POST2);
    }
}
