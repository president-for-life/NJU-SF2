package com.example.cinema.bl.user;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForm;
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
     * 搜索全部Admin
     *
     * @return vo.ResponseVO
     */
    ResponseVO searchAllAdmin();

}
