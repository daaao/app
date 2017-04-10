package io.zhijian.app.controller;

import io.jsonwebtoken.Claims;
import io.zhijian.app.authorization.Token;
import io.zhijian.app.authorization.jwt.JwtConfig;
import io.zhijian.app.authorization.jwt.JwtTokenBuilder;
import io.zhijian.authorization.annotation.Authorization;
import io.zhijian.authorization.annotation.CurrentUser;
import io.zhijian.authorization.manager.TokenManager;
import io.zhijian.base.exception.ApplicationException;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.system.exception.SystemError;
import io.zhijian.system.model.response.UserResponse;
import io.zhijian.system.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:登录控制器. <br/>
 */
@Controller
@RequestMapping(value = "/api")
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    private TokenManager tokenManager;

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Token login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "captcha", required = false) String captcha) throws Exception {
        //验证码校验

        //用户名密码验证
        UserResponse user = userService.auth(username, password);

        if (user != null) {
            Map map = new HashMap();
            map.put("username", user.getUsername());
            String subject = JwtTokenBuilder.buildSubject(map);

            String accessToken = jwtTokenBuilder.buildToken(subject, jwtConfig.getExpiresSecond(), jwtConfig.getBase64Secret());
            String refreshToken = jwtTokenBuilder.buildToken(subject, jwtConfig.getRefreshExpiresSecond(), jwtConfig.getRefreshBase64Secret());

            Token token = new Token();
            token.setAccess_token(accessToken);
            token.setRefresh_token(refreshToken);
            token.setToken_type("bearer");
            token.setExpires_in(jwtConfig.getExpiresSecond());

            //存储到redis
            tokenManager.createRelationship(username, accessToken);

            return token;
        } else {
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), SystemError.LOGIN_FAILED.getMessage());
        }
    }

    /**
     * 刷新token，获取新的token
     *
     * @param refresh_token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    @ResponseBody
    public Token refreshToken(@RequestParam(value = "refresh_token") String refresh_token) throws Exception {

        //验证token
        Claims claims = jwtTokenBuilder.decodeToken(refresh_token, jwtConfig.getRefreshBase64Secret());
        if (claims != null) {
            //如果token验证成功，返回新的token
            String subject = claims.getSubject();//用户信息
            String accessToken = jwtTokenBuilder.buildToken(subject, jwtConfig.getExpiresSecond(), jwtConfig.getBase64Secret());
            String refreshToken = jwtTokenBuilder.buildToken(subject, jwtConfig.getRefreshExpiresSecond(), jwtConfig.getRefreshBase64Secret());

            Token token = new Token();
            token.setAccess_token(accessToken);
            token.setRefresh_token(refreshToken);
            token.setToken_type("bearer");
            token.setExpires_in(jwtConfig.getExpiresSecond());

            //存储到redis
//            tokenManager.createRelationship(username, accessToken);

            return token;
        } else {
            throw new ApplicationException(StatusCode.UNAUTHORIZED.getCode(), "invalid refresh token");
        }
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Integer logout(@RequestHeader(name = "Authorization") String token) {
        //TODO 操蛋的JWT不能从服务端destroy token， logout目前只能在客户端的cookie 或 localStorage/sessionStorage  remove token
        //TODO 准备用jwt生成永久的token，再结合redis来实现Logout。具体是把token的生命周期交给redis来管理，jwt只负责生成token

        try {
            //多端登录，会有多个同一用户名但token不一样的键值对在redis中存在，所以只能通过token删除
//        tokenManager.delRelationshipByKey(user.getUsername());
            tokenManager.delRelationshipByToken(token);//注销成功
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 测试token验证
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    @ResponseBody
    public UserResponse get(@CurrentUser UserResponse user) {
        return userService.get(user.getUsername());
    }
}
