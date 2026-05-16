package com.aqa.utils;

import com.aqa.utils.Logging;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static final Logger log = Logging.get(ApiUtils.class);

    private ApiUtils() {}

    private static RequestSpecification request(String baseUrl) {
        return given().baseUri(baseUrl);
    }

    public static Response get(String baseUrl, String endpoint) {
        log.info("GET → {}{}", baseUrl, endpoint);
        return request(baseUrl)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response getWithParam(String baseUrl, String endpoint,
                                        String paramName, String paramValue) {
        log.info("GET → {}{} [{}={}]", baseUrl, endpoint, paramName, paramValue);
        return request(baseUrl)
                .pathParam(paramName, paramValue)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post(String baseUrl, String endpoint, Object body) {
        log.info("POST → {}{}", baseUrl, endpoint);
        return request(baseUrl)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response deleteWithParam(String baseUrl, String endpoint,
                                           String paramName, int paramValue) {
        log.info("DELETE → {}{} [{}={}]", baseUrl, endpoint, paramName, paramValue);
        return request(baseUrl)
                .pathParam(paramName, paramValue)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}