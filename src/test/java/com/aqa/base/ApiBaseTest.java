package com.aqa.base;

import com.aqa.utils.Logging;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import com.aqa.utils.ConfigReader;
public class ApiBaseTest {

    protected final Logger log = Logging.get(getClass());

    protected static final String DOG_BASE_URL  = ConfigReader.get("dog.api.base.url");
    protected static final String JSON_BASE_URL = ConfigReader.get("json.placeholder.base.url");

    @BeforeClass
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        log.info("API test setup done");
    }
}
