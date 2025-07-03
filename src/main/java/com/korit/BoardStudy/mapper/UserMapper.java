package com.korit.BoardStudy.mapper;

import com.korit.BoardStudy.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
}
