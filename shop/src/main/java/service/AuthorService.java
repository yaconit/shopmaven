package service;
import dao.User.UserMapper;
import org.springframework.stereotype.Service;
import pojo.User;

import javax.annotation.Resource;

@Service
public class AuthorService {
    @Resource
    private UserMapper userMapper;

    public User login(String username, String password){
        return userMapper.login(username,password);
    }

    public User loginByPhone(String phone){
        return userMapper.loginByPhone(phone);
    }
}
