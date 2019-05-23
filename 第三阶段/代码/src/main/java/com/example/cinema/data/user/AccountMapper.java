package com.example.cinema.data.user;

import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @author 梁正川
     */
    int insertOneAccount(User user);

    /**
     * 更新一个账号
     *
     * @author 梁正川
     */
    int updateOneAccount(User user);

    /**
     * 删除一个账号
     *
     * @author 梁正川
     */
    int deleteOneAccount(@Param("id") int id);

    /**
     * 所有管理员账号
     *
     * @author 梁正川
     */
    List<User> selectAdmins();

    /**
     * 所有用户账号
     *
     * @author 梁正川
     */
    List<User> selectUsers();

    /**
     * 根据用户名查找账号
     *
     * @param username 用户名
     * @return po.User
     */
    User getAccountByName(@Param("username") String username);
}
