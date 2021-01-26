package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: qyl
 * @Date: 2021/1/25 11:51
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 校验数据是否可用
     * 实现用户数据校验。主要包括对：手机号、用户名唯一性的校验
     *
     * @param data
     * @param type 1代表检验用户名，2代表检验手机号
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        Boolean boo = userService.checkData(data, type);
        if (boo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/send")
    public ResponseEntity<Void> sendVerifyCode(String phone) {
        Boolean boo = userService.sendVerifyCode(phone);
        if (boo == null || !boo) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/register")
    // @Valid 服务pojo中的验证
    public ResponseEntity<Void> register(@Valid User user, String code) {
        Boolean boo = userService.register(user, code);
        if (boo == null || !boo) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<User> login(String username, String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}
