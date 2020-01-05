package com.hogwarz.interfacetest.api;

import com.hogwarz.interfacetest.Work;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * 基础的User类
 */
public class User {

    /**
     * 查看用户信息
     */
    public Response getInfo(String userId) {
        return given()
                .queryParam("access_token", Work.getInstance().getToken())
                .queryParam("userid",userId)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then().log().all()
                .extract().response();
    }
}
