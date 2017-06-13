package cn.xiaocuoben.apidoc4j.sample.entity;

/**
 * 用户类
 * @author Frank
 * @date 2017-05-10
 */
public class User {

    /**
     * 用户名
     */
    private String username;

    /**
     *
     * @param username 用户名称
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * 子用户
     */
    private User user;
}
