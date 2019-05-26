package com.example.cinema.vo;

import com.example.cinema.po.User;

/**
 * @author 范佳杰
 * @date 2019/4/11 3:22 PM
 */
public class UserVO {
    private Integer id;
    private String username;
    private String password;
    private String role;

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRole() { return role; }

    public void setRole(String rle) { this.role = role; }

    public User getPO() {
        User user = new User();
        user.setId(this.getId());
        user.setRole(this.getRole());
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        return user;
    }
}
