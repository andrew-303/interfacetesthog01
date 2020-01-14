package com.hogwarz.interfacetest.testcase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hogwarz.interfacetest.api.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

    /**
     * 删除成员
     * 先创建成员，再删除成员，保证无前后依赖影响，保证数据一致性
     */
    @Test
    public void delete() {
        String nameNew = "name for testing";
        String userid = "Biyl_1578489753478";

        HashMap<String, Object> data = new HashMap<>();
        data.put("name",nameNew);
        data.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));


        User user = new User();
        user.clone(userid,data).then().body("errcode",equalTo(0));
        user.delete(userid).then().body("errcode",equalTo(0));
        user.getInfo(userid).then().body("errcode",not(equalTo(0)));

    }

    /**
     * 通过读取csv文件作为参数，删除用户
     */
    @ParameterizedTest
    @CsvFileSource(resources = "TestUser.csv")
    public void deleteByParams(String name, String userid) {
        String nameNew = name;
        if (userid.isEmpty()) {
            userid = "biyl_" + System.currentTimeMillis();
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("name",nameNew);
        data.put("department",new int[]{1});
        data.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));

        User user = new User();
        user.create(userid,data).then().body("errcode",equalTo(0));
        user.delete(userid).then().body("errcode",equalTo(0));
        user.getInfo(userid).then().body("errcode",not(equalTo(0)));

    }

    @ParameterizedTest
    @MethodSource("deleteByParamsFromYamlData")
    public void deleteByParamsFromYam(String name, String userid,List<Integer> departs) {
        String nameNew = name;
        if (userid.isEmpty()) {
            userid = "biyl_" + System.currentTimeMillis();
        }

        if (departs == null) {
            departs = Arrays.asList(1);
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("name",nameNew);
        data.put("department",new int[]{1});
        data.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));

        User user = new User();
        user.create(userid,data).then().body("errcode",equalTo(0));
        user.delete(userid).then().body("errcode",equalTo(0));
        user.getInfo(userid).then().body("errcode",not(equalTo(0)));

    }

    //java8中新增的流式参数
    static Stream<Arguments> deleteByParamsFromYamlData() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        //对进行泛型的反序列化，使用TypeReference可以明确的指定反序列化的类型
        //是一个特定数据类型的引用，是因为有些结构无法使用XXX.class，所有采用这种写法
        TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>(){

        };
        List<HashMap<String,Object>> data;
        try {
            //从配置文件中读取配置
            data = mapper.readValue(TestUser.class.getResourceAsStream("TestUser.yaml"),typeRef);
            ArrayList<Arguments> results = new ArrayList<>();
            data.forEach(map -> {
                results.add(arguments(
                        map.get("name").toString(),
                        map.get("userid").toString(),
                        map.get("departs")
                ));
            });

            return results.stream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of();
    }
}
