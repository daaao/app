package io.zhijian.authorization.manager;

/**
 * Token的验证器
 * @author Hao
 * @create 2017-04-10
 */
public interface TokenValidator {
    public boolean validate(String token);
}
