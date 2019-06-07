package com.example.cinema.data.user;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //导入spring测试框架[2]
@SpringBootTest  //提供spring依赖注入
@Transactional  //事务管理，默认回滚,如果配置了多数据源记得指定事务管理器
@DisplayName("Test AccountMapper")
class AccountMapperTest {
    @Autowired
    private AccountMapper accountMapper;

    private static User user;

    @BeforeAll
    void setUp() {
        user = new User();
        user.setRole("admin");
        user.setUsername("test_user");
        user.setPassword("password");
    }

    @Test
    @DisplayName("Add user")
    void addUserTest() {
        accountMapper.insertOneAccount(AccountMapperTest.user);
    }
}