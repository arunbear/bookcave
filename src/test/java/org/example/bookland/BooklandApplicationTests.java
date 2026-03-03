package org.example.bookland;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.bookland.entity.BookEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.*;

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
                .header("Location", matchesRegex(".+/book/[1-9][0-9]*"))
                .statusCode(equalTo(HttpStatus.SC_CREATED))
        ;
    }

    @Test
    void on_creation_a_book_can_be_retrieved_by_id() throws JSONException {
        // given
        var bookDetails = new JSONObject()
                .put("title", "The Tempest")
                ;

        Response creationResponse = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(bookDetails.toString())
                .post("/book")
        ;
        // when
        var book = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(creationResponse.header("Location"))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(BookEntity.class)
        ;
        then(book).isNotNull();
        then(book.id()).isGreaterThan(0L);
        then(book.title()).isEqualTo("The Tempest");
    }
}
