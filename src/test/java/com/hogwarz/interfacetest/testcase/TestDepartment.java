package com.hogwarz.interfacetest.testcase;

import com.hogwarz.interfacetest.api.Department;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestDepartment {

    static Department department = new Department();

    @BeforeAll
    public static void beforeAll() {
        //清理数据
        ArrayList<Integer> ids = department.list(department.parentDepartId).then()
                .extract().body().path("department.findAll { d -> d.parentid==" + department.parentDepartId + " }.id");
        System.out.println("ids: " + ids);
        //forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
        ids.forEach(id -> department.delete(id));
    }

    @Test
    public void list() {
//        department.list(department.parentDepartId).then().body("errmsg",equalTo("ok"));
        //验证通过parentid获取到的id是否正确
        ArrayList<Integer> ids = department.list(department.parentDepartId).then()
                .extract().body().path("department.findAll { d -> d.parentid==" + department.parentDepartId + " }.id");
        System.out.println("ids: " + ids);
    }

    @Test
    public void create() {
        String name = "部门2";
        department.create(name).then().body("errmsg",equalTo("created"));
        department.list(department.parentDepartId)
                .then().body("department.findAll {d->d.name=='" + name +"'}.id",hasSize(1));
    }

    @Test
    public void delete() {
        String name = "部门3";
        int id = department.create(name).then().body("errmsg",equalTo("created"))
                .extract().body().path("id");
        department.delete(id).then().body("errmsg",equalTo("deleted"));
    }
}
