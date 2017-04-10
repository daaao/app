package io.zhijian.app.authorization.jwt;

import io.jsonwebtoken.Claims;
import io.zhijian.authorization.manager.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Hao
 * @create 2017-04-10
 */
@Component
public class JwtTokenValidator  implements TokenValidator {

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public boolean validate(String token) {
        //验证token
        Claims claims = jwtTokenBuilder.decodeToken(token, jwtConfig.getBase64Secret());
        if (claims != null) {
            //如果token验证成功
            String subject = claims.getSubject();//用户信息
            return true;
        }
        return false;
    }
}
