## 通知
该项目已停止更新和维护，如需接口服务请联系vx：E-91022
## 前言
该项目是Java版本的短视频去水印服务端
## 演示

<p align=center><img width='30%' src="https://kodo.xtyu.top/remove-watermark/code.png"/>
<p/>

## 项目结构

```
ToolApi
├── config -- 工具类及通用代码
├── conrtoller -- 系统接口
├── core -- 核心异常拦截
├── exception -- 断言类及自定义异常
├── mapper -- 数据映射
├── model -- 实体
└── service -- 服务逻辑处理
```
## 技术选型
技术 | 说明 | 官网
--------- | ------------- | -------------
H2 | 嵌入式数据库 | https://www.h2database.com/
SpringBoot| 容器+MVC框架 | https://spring.io/projects/spring-boot
MyBatis-Plus | ORM框架 | https://mp.baomidou.com/
