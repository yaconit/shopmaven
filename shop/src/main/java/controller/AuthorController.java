package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.User;
import service.AuthorService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthorController {
    @Resource
    private AuthorService authorService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username, String password){

        Map<String,Object> map = new HashMap<String, Object>();

        if(username.trim().length() == 0){
            map.put("code","0");
            map.put("msg","请填写用户名");
            return map;
        }
        if(password.trim().length() == 0){
            map.put("code","0");
            map.put("msg","请填写密码");
            return map;
        }


        User user = authorService.login(username,password);
        if(null != user){
            map.put("user",user);
            map.put("code","1");
            map.put("msg","登录成功");
        }else{
            map.put("code","0");
            map.put("msg","登录失败");
        }
        return map;
    }
}
