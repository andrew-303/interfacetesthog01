package com.hogwarz.interfacetest.framework;

import io.restassured.response.Response;

import java.util.HashMap;

public class ApiObjectModel {
    //定义基本方法
    public HashMap<String,ApiObjectMethodModel> methods = new  HashMap<>();

    public ApiObjectMethodModel getMethod(String method) {
        return methods.get(method);
    }

    public Response run(String method) {
        return getMethod(method).run();
    }

    public Response run(String method, HashMap<String, Object> params) {
        return getMethod(method).run(params);
    }

}
