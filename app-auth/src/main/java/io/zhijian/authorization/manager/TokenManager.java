package io.zhijian.authorization.manager;

/**
 * 对Token进行管理的接口
 */
public interface TokenManager {

    /**
     * 通过key删除关联关系
     * @param key
     */
    void delRelationshipByKey(String key);

    /**
     * 通过token删除关联关系
     * @param token
     */
    void delRelationshipByToken(String token);

    /**
     * 创建关联关系
     * @param key
     * @param token
     */
    void createRelationship(String key, String token);

    /**
     * 通过token获得对应的key
     * @param token
     * @return
     */
    String getKey(String token);
}
