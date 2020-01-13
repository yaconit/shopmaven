package service;

import org.springframework.stereotype.Service;
import utils.RedisUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TockenService {
    @Resource
    private RedisUtil redis;

    /**
     * 创建Tocken
     * Tocken形式：id-yyyyMMddHHmmss
     *
     * @param id 用户ID
     * @return
     */
    public String create(int id) {
        String tocken = id + "-";
        tocken += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return tocken;
    }

    /**
     * 写入Tocken
     *
     * @param tocken tocken
     * @param id 用户唯一标识
     * @param ttl 有效期
     */
    public void write(String tocken, int id, int ttl) {
        redis.setex(tocken, id + "", ttl);
    }

    /**
     * 置换Tocken
     *
     * @return
     */
    public String replace() {
        return "";
    }

    /**
     * 删除Tocken
     */
    public void delete(String tocken) {
        redis.del(tocken);
    }

    /**
     * 检查Tocken是否有效
     *
     * @return
     */
    public boolean validata(String tocken) {
        return redis.ttl(tocken) > 0;
    }

    /**
     * 检查Tocken是否存在
     *
     * @return
     */
    public boolean exists(String tocken) {
        return redis.exists(tocken);
    }
}
