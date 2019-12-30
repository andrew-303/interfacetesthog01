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
                .extract().body().path("department.findAll {d->d.departid=="+department.parentDepartId+" }.id");
        System.out.println(ids);
        //ids.forEach(id -> department.delete(id));
    }

    @Test
    public void list() {
        department.list(department.parentDepartId).then().body("errmsg",equalTo("ok"));
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
