package com.hogwarz.interfacetest.framework;

import com.hogwarz.interfacetest.Work;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * 读取配置文件
 */
public class ApiObjectMethodModel {

    private HashMap<String,Object> params;
    public HashMap<String,Object> query;
    public HashMap<String,Object> header;
    public HashMap<String,Object> postBody;
    public String postBodyRaw;
    public String method = "get";
    public String url = "";

    public Response run() {
        RequestSpecification request = given();
        //获取access_token
        request.queryParam("access_token", Work.getInstance().getToken());

        /**
         * 判断query是否为空
         */
        if (query != null) {
            query.entrySet().forEach(entry->{
                request.queryParam(entry.getKey(),repalce(entry.getValue().toString()));
                });
        }

        if (header != null) {
            query.entrySet().forEach(entry -> {
                request.header(entry.getKey(), repalce(entry.getValue().toString()));
            });
        }

        if (postBody != null) {
            request.body(postBody);
        }
        if (postBodyRaw != null) {
            request.body(postBodyRaw);
        }

        return request
                .when().log().all().request(method,url)
                .then().log().all().extract().response();
    }

    //替换传参中变量内容
    public String repalce(String raw) {
        for (Map.Entry<String , Object> kv : params.entrySet() ) {
            String mather = "${"+ kv.getKey()+"}";
            if (raw.contains(mather)) {
                System.out.println(kv);
                raw = raw.replace(mather,kv.getValue().toString());
            }
        }
        return raw;
    }

    public Response run(HashMap<String, Object> params) {
        this.params = params;
        return run();
    }
}
