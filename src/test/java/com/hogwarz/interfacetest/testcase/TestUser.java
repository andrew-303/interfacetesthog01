package com.hogwarz.interfacetest.testcase;

import com.hogwarz.interfacetest.api.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class TestUser {

    User user = new User();
    /**
     * 读取用户信息
     */
    @Test
    public void getInfo() {
        String userId = "111111";
        user.getInfo(userId).then().body("name",equalTo("1111111"));
    }

    /**
     * 更新用户信息
     */
    @Test
    public void update() {
        User user = new User();
        String userId = "111111";
        String nameNew = "修改的名字";
        HashMap<String, Object> data = new HashMap<>();
        data.put("userid",userId);
        data.put("name",nameNew);
        user.update(userId,data);
        user.getInfo(userId).then().body("name",equalTo(nameNew));
    }

    /**
     * 创建成员信息
     */
    @Test
    public void create() {
        User user = new User();
        String name = "The First Member";
        String userId = "Biyl_" + System.currentTimeMillis();
        String department = "3";
        String mobile = String.valueOf(System.currentTimeMillis()).substring(0,11);
        String address = "杭州江干区2-6号";

        HashMap<String, Object> data = new HashMap<>();

        data.put("name",name);
        data.put("department",department);
        data.put("mobile",mobile);
        data.put("address",address);

        user.create(userId,data).then().body("errcode",equalTo(0));
        user.getInfo(userId).then().body("name",equalTo(name));

    }

    /**
     * 从模版中Clone成员
     */
    @Test
    public void cloneUser() {
        String nameNew = "name for testing";
        String userid = "biyl_" + System.currentTimeMillis();

        HashMap<String, Object> data = new HashMap<>();
        data.put("name",nameNew);
        data.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));

        User user = new User();
        user.clone(userid,data).then().body("errcode",equalTo(0));
        user.getInfo(userid).then().body("name",equalTo(nameNew));

    }

}
