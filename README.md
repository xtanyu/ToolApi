## 前言
该项目是Java版本的短视频去水印服务端，包括用户签到、积分制度、解析各大短视频无水印视频链接等功能，后续我有时间会陆续加入更多功能
## 演示
![image.png](https://oss.xtyu.top/blog-image/image_1631760814906.png)
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

## 小程序端展示
[视频解析小程序端](https://github.com/xtanyu/parsing-mini)
|![image.png](https://oss.xtyu.top/blog-image/image_1631761041711.png)|![image.png](https://oss.xtyu.top/blog-image/image_1631761083702.png)|![image.png](https://oss.xtyu.top/blog-image/image_1631761100894.png)|![image.png](https://oss.xtyu.top/blog-image/image_1631761154660.png)|
|-------|-------|-------|-------|
|![image.png](https://oss.xtyu.top/blog-image/image_1631761398645.png)|![image.png](https://oss.xtyu.top/blog-image/image_1631761484034.png)|![image.png](https://oss.xtyu.top/blog-image/image_1631761501219.png)|![image.png](https://oss.xtyu.top/blog-image/image_1631761523166.png)|
## 启动流程
### 环境
- JDK1.8
### 开发工具
- IDEA
- 嫌IDEA破解麻烦的可以使用我提供的[IDEA激活码](https://idea.xtyu.top/)
### 流程
1. 修改application.yml配置文件
```yml
spring:
  datasource:
    url: jdbc:h2:file: #自己定义数据库存放路径
    username: #数据库账号
    password: #数据库密码
wx:
  appId: #填写你自己的小程序appId
  secret: #填写你自己的小程序secret    
```
2. IDEA启动项目
3. 浏览器访问 http://127.0.0.1:10521/h2-console
4. 输入账号密码连接H2数据库，账号密码和连接地址记得更改为你自己定义的![](https://oss.xtyu.top/blog-image/WeChat3ea3c7b3af44d356a4108b9255b2704b_1632369390078.png)
5. 登录成功后看到如下管理控制台![](https://oss.xtyu.top/blog-image/WeChat69236ca0bd2fb4a54fa202a7d94c5a2a_1632369358665.png)
6. 执行[sql文件](https://github.com/xtanyu/ToolApi/blob/main/db/schema-h2.sql)
7. [视频解析小程序端](https://github.com/xtanyu/parsing-mini)
