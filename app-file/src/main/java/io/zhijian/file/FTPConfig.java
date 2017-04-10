package io.zhijian.file;


import io.zhijian.utils.PropertyReader;

public class FTPConfig {

    private static String host;
    private static int port;
    private static String username;
    private static String password;

    private static PropertyReader reader;

    static {
        reader = new PropertyReader("/config/ftp.properties");

        host = reader.getProperty("ftp.host");
        port = reader.getInteger("ftp.port");
        username = reader.getProperty("ftp.username");
        password = reader.getProperty("ftp.password");
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        FTPConfig.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        FTPConfig.port = port;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FTPConfig.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        FTPConfig.password = password;
    }
}
