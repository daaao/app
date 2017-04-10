package io.zhijian.app.authorization.resolver;

import io.zhijian.authorization.repository.UserModelRepository;
import io.zhijian.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Hao
 * @create 2017-04-10
 */

@Component
public class UserRepository implements UserModelRepository {

    @Autowired
    private IUserService userService;

    @Override
    public Object getCurrentUser(String key) {
        return userService.get(key);
    }
}
