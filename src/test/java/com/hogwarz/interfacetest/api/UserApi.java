package com.hogwarz.interfacetest.api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class UserApi extends BaseApi{
    public Response get(String userid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid",userid);
        setParams(params);

        return parseSteps();

    }
}
