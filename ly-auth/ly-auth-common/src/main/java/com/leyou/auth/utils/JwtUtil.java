package com.leyou.auth.utils;

import com.leyou.auth.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author: qyl
 * @Date: 2021/1/28 16:59
 */
public class JwtUtil {

    /**
     * 私钥加密Token
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间（单位秒）
     * @return
     */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) {
        return Jwts.builder()
                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getUsername())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 公钥解析Token
     * @param token     用户请求中的Token
     * @param publicKey 公钥
     * @return
     */
    public static Jws<Claims> parseToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parseToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtil.toLong(body.get(JwtConstant.JWT_KEY_ID)),
                ObjectUtil.toString(body.get(JwtConstant.JWT_KEY_USER_NAME))
        );
    }
}
