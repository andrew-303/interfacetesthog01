package com.hogwarz.interfacetest.testcase;

import com.hogwarz.interfacetest.api.User;
import org.junit.jupiter.api.Test;

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
}
