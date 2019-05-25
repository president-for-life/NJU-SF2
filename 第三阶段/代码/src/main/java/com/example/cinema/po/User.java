package com.example.cinema.po;

/**
 * @author 胡文
 * @date 2019/3/23
 */
public class User {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 角色：
     * manager：经理
     * admin：管理员
     * user：（普通）用户
     */
    private String role;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
