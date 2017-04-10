package io.zhijian.app;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author Hao
 * @create 2017-04-10
 */
public class RedisTest {
    private Jedis jedis = null;
    private String key1 = "key1";
    private String key2 = "key2";

    public RedisTest() {
        jedis = new Jedis("192.168.1.200", 6379);
    }

    public static void main(String[] args) {
        RedisTest redisTest = new RedisTest();
        redisTest.isReachable();
        redisTest.testData();
        redisTest.delData();
        redisTest.testExpire();
    }

    public boolean isReachable() {
        boolean isReached = true;
        try {
            jedis.connect();
            jedis.ping();
            // jedis.quit();
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            isReached = false;
        }

        System.out
                .println("The current Redis Server is Reachable:" + isReached);
        return isReached;
    }

    public void testData() {
        jedis.set("key1", "data1");

        System.out.println("Check status of data existing:"
                + jedis.exists(key1));
        System.out.println("Get Data key1:" + jedis.get("key1"));

        long s = jedis.sadd(key2, "data2");
        System.out.println("Add key2 Data:" + jedis.scard(key2)
                + " with status " + s);
    }

    public void delData() {
        long count = jedis.del(key1);

        System.out.println("Get Data Key1 after it is deleted:"
                + jedis.get(key1));
    }

    public void testExpire() {
        long count = jedis.expire(key2, 5);

        try {
            Thread.currentThread().sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (jedis.exists(key2)) {
            System.out
                    .println("Get Key2 in Expire Action:" + jedis.scard(key2));
        } else {
            System.out.println("Key2 is expired with value:"
                    + jedis.scard(key2));
        }
    }
}
