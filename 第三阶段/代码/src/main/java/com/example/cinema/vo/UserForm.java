package com.example.cinema.vo;

import com.example.cinema.po.User;

/**
 * @author 胡文
 * @date 2019/3/23
 */
public class UserForm {

    /**
     * 角色
     */
    private String role;

    /**
     * 用户名，不可重复
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getPO() {
        User user = new User();
        user.setRole(this.getRole());
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        return user;
    }
}
