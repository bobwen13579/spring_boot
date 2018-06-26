# 项目描述

### 开发工具

- IntelliJ *IDEA* 2017
- jdk1.8
- SpringBoot 1.5.9RELEASE
- mysql8.0.1
- navicat

### 知识储备

- java基础知识
  - 类和接口
  - Des加密解密算法
  - mysql
- spring boot
  - pom.xml 依赖
  - Spring Initializer快速创建项目
  - 配置文件（application.properties，application.yml 语法注入等）
  - 静态资源文件映射规则
  - thymeleaf使用和语法规则
  - 连接mysql数据库
  - 整合Mybatis

### 项目分析

#### dao包：

 声明了UserDao接口：定义了对数据库插入和查询的操作

#### enti包：

封装了User类：用户的用户名和密码

#### controller：

UserController:实现UserDao接口，通过thymeleaf控制不同页面的跳转，实现字符串加密和解密的函数，将页面捕获的密码加密存到数据库中，数据库查询的密码解密后再与登陆的支付进行匹配。

#### 

```java

```



#### 页面

主要用的是表单操作，没有进行美化

#### 配置文件

```xml
#配置文件，主要是连接mysql数据库
spring.datasource.url=jdbc:mysql://localhost:3306/db_user
spring.datasource.username=root
spring.datasource.password=13579
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

pom.xml主要配置项

`切记：导入thymeleaf一定要科学上网，不然会包导入失败而报错`

```xml
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		</dependency>
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
```

