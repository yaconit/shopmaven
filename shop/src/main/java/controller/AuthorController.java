package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.User;
import service.AuthorService;
import service.SMSService;
import service.TockenService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthorController {
    @Resource
    private AuthorService authorService;
    @Resource
    private TockenService tockenService;
    @Resource
    private SMSService smsService;

    /**
     * 用户名密码登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
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

            // 创建 Tocken
            String tocken = tockenService.create(user.getId());
            // 将 Tocken 写入 Redis
            tockenService.write(tocken, user.getId(),15);
            // 将 Tocken 发送给客户端
            map.put("tocken",tocken);

        }else{
            map.put("code","0");
            map.put("msg","登录失败");
        }
        return map;
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String tocken = request.getHeader("tocken");

        if(!tockenService.exists(tocken)){
            map.put("code","0");
            map.put("msg","请先登录在进行退出");
            return map;
        }

        tockenService.delete(tocken);
        map.put("code","1");
        map.put("msg","退出成功");
        return map;
    }

    /**
     * 发送短信验证码
     * @return
     */
    @RequestMapping("/send/sms")
    @ResponseBody
    public Object sendSMS(String phone){
        Map<String, Object> map = new HashMap<>();
        Integer code = smsService.createCode();
        if(smsService.send(phone,code.toString(),10)){

            // 将验证码保存到 Redis 中
            smsService.saveCode(phone,code.toString(),10);

            map.put("code","1");
            map.put("msg","发送成功");
            map.put("yzm",code);
        }else{
            map.put("code","0");
            map.put("msg","发送失败");
        }
        return map;
    }

    /**
     * 使用手机号登录
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping(value = "/login/phone",method = RequestMethod.POST)
    @ResponseBody
    public Object loginByPhone(String phone,String code){
        Map<String, Object> map = new HashMap<>();

        if(smsService.valicode(phone,code)){

            // 根据手机号获取用户信息
            User user = authorService.loginByPhone(phone);

            // 创建并写入 Tocken
            String tocken = tockenService.create(user.getId());
            tockenService.write(tocken,user.getId(),90);

            map.put("code","1");
            map.put("msg","登录成功");
            map.put("tocken",tocken);
        }else{
            map.put("code","0");
            map.put("msg","登录失败");
        }

        return map;
    }

}
