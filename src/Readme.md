代码结构：
api:
    类似于PageObject中的PO
testcase:
    测试用例，调用PO中的实际方法
framework：
    封装的框架方法，用于读取yaml文件做数据驱动
    
实现PO的模型定义：
PO的配置文件:UserApi.yaml
PO的代码：UserApi
BaseApi:定义基础的解析步骤代码
ApiObjectModel
ApiObjectMethodModel
          