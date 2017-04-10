package io.zhijian.app.authorization.jwt;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

/**
 * JWT 处理token的生成、解码
 * @author Hao
 * @create 2017-04-08
 */
@Component
public class JwtTokenBuilder {

    /**
     * 生成token
     * @param subject
     * @param ttlMillis
     * @param base64Secret
     * @return
     * @throws Exception
     */
    public String buildToken(String subject, long ttlMillis, String base64Secret) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        SecretKey key = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);

        //设置token过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
//            builder.setExpiration(exp).setNotBefore(now);
        }
        return builder.compact();
    }

    /**
     * 解码token
     * @param jwt
     * @param base64Secret
     * @return
     */
    public Claims decodeToken(String jwt, String base64Secret) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            //过期
            return null;
        } catch (SignatureException e) {
            //签名错误
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String buildSubject(Map map) {
        String subject = JSON.toJSONString(map);
        return subject;
    }
}
