package com.hogwarz.interfacetest.api;

import com.hogwarz.interfacetest.Work;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * 标签管理基类
 */
public class TagMgment {

    /**
     * 创建标签
     */
    public Response createTag(String tagName) {
        Map<String, Object> data = new HashMap<>();
        data.put("tagname",tagName);
        return given().queryParam("access_token", Work.getInstance().getToken())
            .contentType(ContentType.JSON)
            .body(data)
            .when().log().all()
            .post("https://qyapi.weixin.qq.com/cgi-bin/tag/create")
            .then().log().all()
            .body("errmsg",equalTo("created"))
            .extract().response();
    }

    /**
     * 创建标签
     */
    public Response createTag(String tagName,int tagId) {
        Map<String, Object> data = new HashMap<>();
        data.put("tagname",tagName);
        data.put("tagid",tagId);
        return given().queryParam("access_token", Work.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/tag/create")
                .then().log().all()
                .body("errmsg",equalTo("created"))
                .extract().response();
    }

}
