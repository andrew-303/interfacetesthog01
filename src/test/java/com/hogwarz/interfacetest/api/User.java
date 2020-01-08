package com.hogwarz.interfacetest.api;

import com.hogwarz.interfacetest.Work;
import io.restassured.response.Response;

import java.util.HashMap;

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

    /**
     * 更新成员信息
     * 开始传入复杂参数数据,如HashMap
     */
    public Response update(String userId, HashMap<String, Object> data) {
        //传的参数内容
        data.put("userid",userId);

        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().log().all()
                .extract().response();
    }

    /**
     * 创建用户
     */
    public Response create(String userId,HashMap<String,Object> data) {
        data.put("userid",userId);

        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all()
                .extract().response();
    }

}
