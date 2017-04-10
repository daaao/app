package io.zhijian.app.authorization.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author Hao
 * @create 2017-04-08
 */

@Repository
public class JwtConfig {

    @Value("#{propertiesReader['jwt.base64.secret']}")
    private String base64Secret;

    @Value("#{propertiesReader['jwt.refresh.base64.secret']}")
    private String refreshBase64Secret;

    @Value("#{propertiesReader['jwt.expires.second']}")
    private long expiresSecond;

    @Value("#{propertiesReader['jwt.refresh.expires.second']}")
    private long refreshExpiresSecond;

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public long getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(long expiresSecond) {
        this.expiresSecond = expiresSecond;
    }

    public long getRefreshExpiresSecond() {
        return refreshExpiresSecond;
    }

    public void setRefreshExpiresSecond(long refreshExpiresSecond) {
        this.refreshExpiresSecond = refreshExpiresSecond;
    }

    public String getRefreshBase64Secret() {
        return refreshBase64Secret;
    }

    public void setRefreshBase64Secret(String refreshBase64Secret) {
        this.refreshBase64Secret = refreshBase64Secret;
    }
}
