# 项目描述

### 文档说明

- ### spring_login:     登陆注册程序

- ### springboot_practice：刚学习练习的demo

- ### SpringBoot学习笔记:   学习SpringBoot的笔记

- ### sucess_image：    运行成功的截图

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

####  Des加密解密实现

```java
    private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 };
    @SuppressWarnings("restriction")
    public static String encryptBasedDes(String data) {
        String encryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            // log.error("加密错误，错误信息：", e);
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    @SuppressWarnings("restriction")
    public static String decryptBasedDes(String cryptData) {
        String decryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串进行解码，解码为为字节数组，并解密
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }
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

