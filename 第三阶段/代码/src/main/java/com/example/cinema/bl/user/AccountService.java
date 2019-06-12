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
     * @author 胡文
     */
    ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存在session中
     *
     * @author 胡文
     */
    UserVO login(UserForm userForm);

    /**
     * @author 徐志乐
     */
    ResponseVO updateUser(UserForm userUpdateForm);


    /**
     * @author 徐志乐
     */
    ResponseVO searchAllAdmin();

    /**
     * @author 徐志乐
     */
    ResponseVO deleteOneAccount(Integer id);

}
