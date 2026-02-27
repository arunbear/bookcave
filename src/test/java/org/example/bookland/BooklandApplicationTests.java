package org.example.bookland;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BooklandApplicationTests {

    @LocalServerPort
    private int localServerPort;

    @BeforeEach
    public void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void accepts_a_book_creation_message() throws JSONException {
        var bookDetails = new JSONObject()
                .put("title", "The Tempest")
                ;

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(bookDetails.toString())
                .post("/book")
                .then()
                .log().body()
                .statusCode(equalTo(HttpStatus.SC_CREATED))
        ;
    }

}
