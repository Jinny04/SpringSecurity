package com.ohgiraffers.session.user.model.dao;

import com.ohgiraffers.session.user.model.dto.SignupDTO;
import com.ohgiraffers.session.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int regist(SignupDTO newUserInfo);

    UserDTO findByUsername(String username);
}
