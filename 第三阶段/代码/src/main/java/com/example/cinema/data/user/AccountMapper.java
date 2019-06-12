package com.example.cinema.data.user;

import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 胡文
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * @author 胡文
     */
    int insertOneAccount(User user);

    /**
     * @author 徐志乐
     */
    int updateOneAccount(User user);

    /**
     * @author 徐志乐
     */
    int deleteOneAccount(@Param("id") int id);

    /**
     * @author 徐志乐
     */
    List<User> selectAdmins();

    /**
     * @author 徐志乐
     */
    List<User> selectUsers();

    /**
     * 根据用户名查找账号
     *
     * @author 胡文
     */
    User getAccountByName(@Param("username") String username);

}
