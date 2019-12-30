package com.hogwarz.interfacetest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class testClassicDepart {
    static String token;
    static int parentDepartId = 2;

    @BeforeAll
    public static void getToken() {
        token = given()
                .param("corpid", "ww18ebd2532d051a7c")
                .param("corpsecret", "SlvETvFEHRIz3XCnRPrDKnf8y8EWuyB0L79DYE1xErs")
                .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .log().all()
                .body("errcode", equalTo(0))
                .extract()
                .body().path("access_token");
    }

    /**
     * 创建部门
     */
    @Test
    public void departCreate() {
        //构造json类型的body
        Map<String, Object> data = new HashMap<>();
        data.put("name","测试1部");
        data.put("parentid",parentDepartId);

        given()
                .queryParam("access_token",token)
                .contentType(ContentType.JSON)
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .body("errcode",equalTo(0));
    }

    /**
     * 删除部门
     */
    @Test
    public void departList() {
        given()
                .param("access_token",token)
                .param("id",parentDepartId)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .body("errmsg",equalTo("ok"));
    }
}
