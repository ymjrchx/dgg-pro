package net.dgg.bigdata.sjjs.web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName: JwtUtils
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/13 9:27
 */

@Component
public class JwtUtils {

    @Value("${jwt.config.key}")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    /**
     * 生成token
     *
     * @param id
     * @param subject
     * @param
     * @return
     */
    public String createJWT(String id, String subject, Map map) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);
        builder.setExpiration(new Date(nowMillis + 1000 * 60 * 60 * 4));
        if (map != null) {
            builder.setClaims(map);
        }
        return builder.compact();
    }


    /**
     * 解析token
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}