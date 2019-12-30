package com.hogwarz.interfacetest.api;

import com.hogwarz.interfacetest.Work;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * 部门基类
 */
public class Department {

    public int parentDepartId = 2;

    /**
     * 获取部门列表
     * @param id
     * @return
     */
    public Response list(int id) {
        return given()
                .queryParam("access_token", Work.getInstance().getToken())
                .queryParam("id",parentDepartId)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .body("errcode",equalTo(0))
                .extract().response();
    }

    /**
     * 创建部门
     */
    public Response create(String name, int parentid) {
        Map<String, Object> data = new HashMap<>();
        data.put("name",name);
        data.put("parentid",parentid);

        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .body("errcode",equalTo(0))
                .extract().response();
    }

    /**
     * 默认根据parentDepartId来创建
     * @param name
     * @return
     */
    public Response create(String name) {
        return create(name,parentDepartId);
    }


    /**
     * 删除部门
     */
    public Response delete(int id) {
        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .queryParam("id",id)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all()
                .body("errcode",equalTo(0))
                .extract().response();
    }

}
