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
     * 更新账号信息
     *
     * @author 徐志乐
     */
    ResponseVO updateUser(UserForm userUpdateForm);


    /**
     * 搜索全部管理员账号信息
     *
     * @author 徐志乐
     */
    ResponseVO searchAllAdmin();

    /**
     * 删除账号
     *
     * @author 徐志乐
     */
    ResponseVO deleteOneAccount(Integer id);

}
