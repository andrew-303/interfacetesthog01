package com.hogwarz.interfacetest.testcase;

import com.hogwarz.interfacetest.api.TagMgment;
import org.junit.jupiter.api.Test;

/**
 * 测试标签管理相关接口
 */
public class TestTagMgment {

     static TagMgment tagMgment = new TagMgment();

    /**
     * 创建标签
     */
    @Test
    public void testCreateTag() {
        String tagName = "接口测试1";
        //int tagId = 11;
        tagMgment.createTag(tagName);
    }
}
