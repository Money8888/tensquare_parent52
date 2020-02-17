package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder()
                .setId("666")
                .setSubject("胡可")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "eric")
                .setExpiration(new Date(new Date().getTime() + 600000))
                .claim("role", "admin");
        System.out.println(builder.compact());
    }
}
