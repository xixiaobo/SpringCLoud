package com.xi.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import com.xi.demo.bean.Users;
import org.apache.ibatis.annotations.Select;
/**
 * @author : xixiaobo@gmail.com
 * @date : 2019/7/12 10:45
 * @Description:
 */
@Mapper
public interface UsersMapper {
    @Select("select count(*)from users")
    int getUserCount();

    List<Users> getAllUsers();
}
