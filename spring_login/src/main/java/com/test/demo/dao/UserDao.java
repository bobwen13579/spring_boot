package com.test.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.test.demo.enti.User;

@Mapper
public interface UserDao {
    @Insert("INSERT INTO user VALUES(#{username}, #{password})")
    void save(@Param("username")String username,@Param("password")String password);

    @Select("SELECT * FROM user WHERE username= #{username}")
    User findByUname(@Param("username")String username);
}

