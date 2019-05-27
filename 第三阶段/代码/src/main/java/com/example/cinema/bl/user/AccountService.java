package com.example.cinema.bl.user;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.UserUpdateForm;
import com.example.cinema.vo.UserVO;

/**
 * @author 胡文
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return vo.ResponseVO
     */
    ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存在session中
     *
     * @return vo.UserVO
     */
    UserVO login(UserForm userForm);


    /**
     * 更新一个user
     *
     * @return vo.ResponseVO
     */

    ResponseVO updateUser(UserUpdateForm userUpdateForm);


    /**
     * 搜索全部Admin
     *
     * @return vo.ResponseVO
     */

    ResponseVO searchAllAdmin();

    /**
     * 搜索全部Admin
     *
     * @param id
     * @return vo.ResponseVO
     */

    ResponseVO deleteOneAccount(int id);

}
