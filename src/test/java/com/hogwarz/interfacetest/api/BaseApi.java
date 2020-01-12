package com.hogwarz.interfacetest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hogwarz.interfacetest.framework.ApiObjectModel;

import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;

public class BaseApi {
    ApiObjectModel model = new ApiObjectModel();
    HashMap<String,Object> params;

    /**
     * 解析测试步骤的方法
     */
    public Response parseSteps() {

        System.out.println("进行测试步骤解析");
        //Thread.currentThread().getStackTrace()是JDK自带的功能，用于获得当前函数的调用栈。
        // 第0个是自身，第1个是调用getStackTrace的方法，
        // 第2个是更父类的调用方法，也就是调用了parseSteps()的调用者的名字
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println("method: " + method);
        System.out.println("getStackTrace()[0]: " + Thread.currentThread().getStackTrace()[0].getMethodName());
        System.out.println("getStackTrace()[1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
        if (model.methods.entrySet().isEmpty()) {
            System.out.println("The pom is first load!!!");
            String path = "/" + this.getClass().getCanonicalName().replace(".","/")+ ".yaml";
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                //这是jackson的标准的yaml序列化的过程。一个类对应一个数据文件，
                // ApiObjectMethodModel类中的public字段代表了yaml中的某个key
                model = mapper.readValue(
                        BaseApi.class.getResourceAsStream(path), ApiObjectModel.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return model.run(method,params);
    }

    /**
     * 把原来参数的数据，以K-v形式传过去
     * 需要动态传参的部分，通过这个方法传递
     * @param data
     */
    public void setParams(HashMap<String, Object> data) {
        System.out.println("这里进行动态传参：" + data.entrySet());
        params = data;
    }
}
