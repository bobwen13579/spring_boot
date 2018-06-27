package com.test.demo.controller;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.demo.dao.UserDao;
import com.test.demo.enti.User;

@Controller
public class UserController {

    @Autowired

    //声明userDao接口，这里不清纯为什么报错"Could not autowire. No beans of 'UserDao' type found"
    private UserDao userDao;

    @RequestMapping("/regist")
    public String regist() {
        return "regist";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


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

    @RequestMapping("/success")
    public String success(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String s1 = encryptBasedDes(password);
        userDao.save(username, s1);
        return "success";
    }

    @RequestMapping("/Loginsuccess")
    public String successLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userDao.findByUname(username);
        if(decryptBasedDes(user.getPassword()).equals(password)) {
            return "successLogin";
        }
        return "failure";
    }
}
