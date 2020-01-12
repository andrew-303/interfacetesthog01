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

    //用于承载外部传递参数的
    private HashMap<String,Object> params;

    //对应yaml文件中对应的内容
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

    public Response run(HashMap<String, Object> params) {
        this.params = params;
        return run();
    }

    //替换传参中变量内容
    public String repalce(String raw) {
        System.out.println("参数raw: " + raw);
        //配置文件中的参数替换，将userid: ${userid}替换
        for (Map.Entry<String , Object> kv : params.entrySet() ) {
            String mather = "${"+ kv.getKey()+"}";
            if (raw.contains(mather)) {
                System.out.println("传递过来的参数是: " + kv);
                raw = raw.replace(mather,kv.getValue().toString());
            }
        }
        return raw;
    }


}
