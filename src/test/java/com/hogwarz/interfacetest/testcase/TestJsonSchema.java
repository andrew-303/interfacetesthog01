package com.hogwarz.interfacetest.testcase;

import com.hogwarz.interfacetest.api.User;
import com.hogwarz.interfacetest.api.UserApi;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * 验证jsonschema的断言功能
 */
public class TestJsonSchema {

    @Test
    void schema_wrong() {
        UserApi userApi = new UserApi();
        userApi.get("Biyl_1578552417150").then().body(matchesJsonSchemaInClasspath("com/hogwarz/interfacetest/testcase/user_get_schema.json"));
    }

    @Test
    void schema_right() {
        UserApi userApi = new UserApi();
        userApi.get("Biyl_1578552417150").then().body(matchesJsonSchemaInClasspath("com/hogwarz/interfacetest/testcase/user_get_schema_right.json"));
    }
}
