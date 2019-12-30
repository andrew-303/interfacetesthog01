package com.hogwarz.interfacetest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Work {

    private static Work work;
    String token;

    //单例模式
    private Work() {
    }

    public static Work getInstance() {
        if (work == null) {
            work = new Work();
        }
        return work;
    }


    public String getToken() {
        if (token == null) {
            token = given()
                    .param("corpid", "ww18ebd2532d051a7c")
                    .param("corpsecret", "SlvETvFEHRIz3XCnRPrDKnf8y8EWuyB0L79DYE1xErs")
                    .when().log().all()
                    .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                    .then().log().all()
                    .body("errcode", equalTo(0))
                    .extract()
                    .body().path("access_token");
            System.out.println("access_token: " + token);
        }
        return token;
    }

    @Test
    public void getTokenTest() {

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
        System.out.println("access_token: " + token);


    }
}
