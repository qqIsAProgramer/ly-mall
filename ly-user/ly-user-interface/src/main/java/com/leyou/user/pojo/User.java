package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Author: qyl
 * @Date: 2021/1/23 22:08
 */
@Data
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // id自增长式
    private Long id;

    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    private String username;

    @JsonIgnore  // 不在json中显示
    @Length(min = 4, max = 30, message = "密码只能在4~30位之间")
    private String password;

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    /*
    * ^1[35678]\d{9}$
    * ^ 代表字符串开始位置，$ 代表字符串结束位置
    * 1 代表字符串第一位为1
    * [35678] 代表字符串第二位为3或5或6或7或8
    * \d 代表一个数字字符
    * {9} 代表一共有9个字符
    * */
    private String phone;

    private Date created;  // 创建时间

    @JsonIgnore
    private String salt;  // 密码盐值
}
