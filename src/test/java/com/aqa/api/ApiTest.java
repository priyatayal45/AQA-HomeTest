package com.aqa.api;

import com.aqa.api.ApiBaseTest;
import com.aqa.api.DogApiEndpoints;
import com.aqa.api.JsonPlaceholderEndpoints;
import com.aqa.utils.ApiUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest extends ApiBaseTest {

    @BeforeClass
    public void setBaseUrl() {
        RestAssured.baseURI = DOG_BASE_URL;
        log.info("Base URI set to: {}", DOG_BASE_URL);
    }
  
    @Test(description = "TC01 - Random dog image returns 200 and valid image URL")
    public void testGetRandomDogImage() {
        log.info("TC01 - Get random dog image");
        Response response = ApiUtils.get(DOG_BASE_URL,DogApiEndpoints.RANDOM_IMAGE);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("status"), equalTo("success"));
        assertThat(response.jsonPath().getString("message"), containsString("https://"));
        assertThat(response.jsonPath().getString("message"),matchesPattern(".*\\.(jpg|jpeg|png)$"));
        log.info("TC01 passed — URL: {}", response.jsonPath().getString("message"));
    }

    @Test(description = "TC02 - All breeds list is non-empty and contains known breed")
    public void testGetAllBreeds() {
        log.info("TC02 - Get all breeds");
        Response response = ApiUtils.get(DOG_BASE_URL,DogApiEndpoints.ALL_BREEDS);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("status"), equalTo("success"));
        assertThat(response.jsonPath().getMap("message"), is(not(anEmptyMap())));
        assertThat(response.jsonPath().getString("message.labrador"), notNullValue());
        log.info("TC02 passed — breeds map is non-empty");
    }

    @DataProvider(name = "breeds")
    public Object[][] breedProvider() {
        return new Object[][]{{"labrador"}, {"poodle"},{"beagle"}};
    }

    @Test(dataProvider = "breeds", description = "TC03 - Image by breed returns correct breed URL")
    public void testGetImageByBreed(String breed) {
        log.info("TC03 - Get image for breed: {}", breed);
        Response response = ApiUtils.getWithParam(DOG_BASE_URL,DogApiEndpoints.IMAGE_BY_BREED, "breed", breed);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("status"), equalTo("success"));
        assertThat(response.jsonPath().getString("message"), containsString(breed));
        log.info("TC03 passed for breed: {}", breed);
    }

    @Test(description = "TC04 - Invalid breed returns 404 with error message")
    public void testInvalidBreedReturns404() {
        log.info("TC04 - Invalid breed check");

        Response response = ApiUtils.get(DOG_BASE_URL,DogApiEndpoints.INVALID_BREED);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.jsonPath().getString("status"), equalTo("error"));
        assertThat(response.jsonPath().getString("message"), notNullValue());
        log.info("TC04 passed — 404 received for invalid breed");
    }

    @Test(description = "TC05 - Create a new post returns 201 with correct data")
    public void testCreatePost() {
        RestAssured.baseURI = JSON_BASE_URL;
        log.info("TC05 - POST create new post");

        Map<String, Object> body = new HashMap<>();
        body.put("title", "AQA Test Post");
        body.put("body", "This is a test post body");
        body.put("userId", 1);

        Response response = ApiUtils.post(JSON_BASE_URL,JsonPlaceholderEndpoints.POSTS, body);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.jsonPath().getString("title"), equalTo("AQA Test Post"));
        assertThat(response.jsonPath().getString("body"), equalTo("This is a test post body"));
        assertThat(response.jsonPath().getInt("userId"), equalTo(1));
        assertThat(response.jsonPath().getInt("id"), notNullValue());
        log.info("TC05 passed — post created with id: {}", response.jsonPath().getInt("id"));
    }

    // TC06: Delete a post (DELETE)
    @Test(description = "TC06 - Delete a post returns 200 with empty body")
    public void testDeletePost() {
        RestAssured.baseURI = JSON_BASE_URL;
        log.info("TC06 - DELETE post with id 1");

        Response response = ApiUtils.deleteWithParam(JSON_BASE_URL,JsonPlaceholderEndpoints.POST_BY_ID, "id", 1);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody().asString(), containsString("{}"));
        log.info("TC06 passed — post deleted successfully");
    }
}
