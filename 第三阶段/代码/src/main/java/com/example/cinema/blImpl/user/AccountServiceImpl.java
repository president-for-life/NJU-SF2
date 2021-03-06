package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 胡文
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            accountMapper.insertOneAccount(userForm.getPO());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user
                || !user.getPassword().equals(userForm.getPassword())) {// 用户名不存在或密码错误
            return null;
        }
        return new UserVO(user);
    }

    /*
     * @author Xu
     * 搜索返回所有的user属性role为admin的对象
     */

    @Override
    public ResponseVO searchAllAdmin() {
        try {
            return ResponseVO.buildSuccess(userList2UserVOList(accountMapper.selectAdmins()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /*
     * @author Xu
     * 更新一个user对象
     */

    @Override
    public ResponseVO updateUser(UserForm userUpdateForm) {
        try {
            accountMapper.updateOneAccount(userUpdateForm.getPO());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }


    /*
     * @author Xu
     * 删除一个user对象
     */

    @Override
    public ResponseVO deleteOneAccount(Integer id) {
        try {
            int userId = id;
            accountMapper.deleteOneAccount(userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<UserVO> userList2UserVOList(List<User> userList) {
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            userVOList.add(new UserVO(user));
        }
        return userVOList;
    }

}
