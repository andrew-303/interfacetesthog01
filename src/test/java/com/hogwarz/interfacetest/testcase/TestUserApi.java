package com.hogwarz.interfacetest.testcase;

import com.hogwarz.interfacetest.api.UserApi;
import org.junit.jupiter.api.Test;

public class TestUserApi {

    @Test
    public void get() {
        UserApi user = new UserApi();
        user.get("Biyl_1578552417150");
    }
}
