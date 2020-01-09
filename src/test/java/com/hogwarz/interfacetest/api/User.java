package com.hogwarz.interfacetest.api;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.hogwarz.interfacetest.Work;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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

    /**
     * 通过模版clone用户
     */
    public Response clone(String userid, HashMap<String, Object> data) {
        data.put("userid",userid);
        //使用模版技术
        String body = template("/com/hogwarz/interfacetest/api/user.json", data);
        System.out.println("模版读取内容："+ body);
        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(body)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all()
                .extract().response();

    }


    /**
     * 模版处理方法
     */
    public String template(String templatePath,HashMap<String,Object> data) {
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(this.getClass().getResource(templatePath).getPath());
        mustache.execute(writer,data);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}
