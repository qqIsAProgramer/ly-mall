package com.leyou.user.service;

import com.leyou.common.utils.NumberUtil;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qyl
 * @Date: 2021/1/25 9:58
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AmqpTemplate amqpTemplate;

    private static final String KEY_PREFIX = "user:code:phone:";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 校验数据是否可用
     * 实现用户数据校验。主要包括对：手机号、用户名唯一性的校验
     *
     * @param data
     * @param type 1代表检验用户名，2代表检验手机号
     * @return
     */
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(record) == 0;
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    public Boolean sendVerifyCode(String phone) {
        // 生成6位验证码
        String code = NumberUtil.generateCode(6);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            // 发送消息到RabbitMQ
            amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);
            // 将code存入redis，存储5min
            stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone:{}, code:{}", phone, code);
            return false;
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     * @return
     */
    public Boolean register(User user, String code) {
        // 检验短信验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!StringUtils.equals(code, cacheCode)) {
            return false;
        }
        // 生成盐
        String salt = CodecUtil.generateSalt();
        user.setSalt(salt);

        // 对密码加密
        user.setPassword(CodecUtil.md5Hex(user.getPassword(), salt));

        // 强制设置不能指定的参数为null
        user.setId(null);
        user.setCreated(new Date());
        // 添加到数据库
        boolean b = userMapper.insertSelective(user) == 1;

        if (b) {
            // 注册成功，删除redis中的记录
            stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return b;
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 检验密码
        if(!user.getPassword().equals(CodecUtil.md5Hex(user.getPassword(), user.getSalt()))) {
            return null;
        }
        return user;
    }
}
