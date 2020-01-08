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
        String userId = "The First Member";

    }
}
