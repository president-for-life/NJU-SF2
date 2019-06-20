package com.example.cinema.data.user;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //导入spring测试框架
@SpringBootTest  //提供spring依赖注入
@Transactional  //事务管理，默认回滚,如果配置了多数据源记得指定事务管理器
@DisplayName("AccountMapper单元测试")
class AccountMapperTest {
    @Autowired
    private AccountMapper accountMapper;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User();
        user.setRole("admin");
        user.setUsername("test_user");
        user.setPassword("password");
    }

    @Test
    @DisplayName("insertOneAccount")
    void insertOneAccount() {
        accountMapper.insertOneAccount(AccountMapperTest.user);

        User user = accountMapper.getAccountByName(AccountMapperTest.user.getUsername());
        assertEquals("admin", user.getRole());
        assertEquals("password", user.getPassword());
    }

    @Test
    @DisplayName("updateOneAccount")
    void updateOneAccount() {
        accountMapper.insertOneAccount(AccountMapperTest.user);

        User user = accountMapper.getAccountByName(AccountMapperTest.user.getUsername());
        user.setPassword("123456");

        accountMapper.updateOneAccount(user);

        user = accountMapper.getAccountByName(AccountMapperTest.user.getUsername());
        assertEquals("123456", user.getPassword());
    }

    @Test
    @DisplayName("deleteOneAccount")
    void deleteOneAccount() {
        accountMapper.insertOneAccount(AccountMapperTest.user);

        User user = accountMapper.getAccountByName(AccountMapperTest.user.getUsername());
        int id = user.getId();
        accountMapper.deleteOneAccount(id);

        user = accountMapper.getAccountByName(AccountMapperTest.user.getUsername());

        assertNull(user);
    }

    @Test
    @DisplayName("selectAdmins")
    void selectAdmins() {
        int initialSize = accountMapper.selectAdmins().size();

        accountMapper.insertOneAccount(AccountMapperTest.user);

        User user2 = new User();
        user2.setRole("admin");
        user2.setUsername("test_user2");
        user2.setPassword("password");
        accountMapper.insertOneAccount(user2);

        List<User> list = accountMapper.selectAdmins();
        assertEquals(initialSize + 2, list.size());
    }
}