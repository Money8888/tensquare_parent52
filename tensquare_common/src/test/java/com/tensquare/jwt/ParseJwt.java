package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * Token解析
 */
public class ParseJwt {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLog6Hlj68iLCJpYXQiOjE1ODA1MzcxMDIsImV4cCI6MTU4MDUzNzcwMiwicm9sZSI6ImFkbWluIn0.COGJeJ283MEkoZMv5FLEcUVQYGPOawJlYz-2YogRrCQ";
        Claims claims = Jwts.parser()
                .setSigningKey("eric")
                .parseClaimsJws(token)
                .getBody();

        System.out.println("用户id:" + claims.getId());
        System.out.println("用户名:" + claims.getSubject());
        System.out.println("登陆时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色:" + claims.get("role"));
    }
}
