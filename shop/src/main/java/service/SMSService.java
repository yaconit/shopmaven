package service;

import org.springframework.stereotype.Service;
import utils.RedisUtil;
import utils.SMSUtil;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class SMSService {
    @Resource
    private SMSUtil sms;
    @Resource
    private RedisUtil redis;

    /**
     * 创建验证码
     * @return
     */
    public Integer createCode(){
        return (int)(Math.random() * (9999 - 1000) + 1000);
    }

    /**
     * 发送验证码
     * @param phone
     * @param code
     * @param minute
     * @return
     */
    public boolean send(String phone,String code, int minute){
        return sms.send(phone,code,minute);
    }

    /**
     * 保存验证码
     * @param phone
     * @param code
     */
    public void saveCode(String phone, String code,int minute){
        redis.setex(phone,code,minute*60);
    }

    /**
     * 验证验证码
     * @param phone
     * @param code
     * @return
     */
    public boolean valicode(String phone,String code){
        if(!redis.exists(phone)) {
            return false;
        }
        String _code = redis.get(phone);
        return _code.equals(code);
    }
}
