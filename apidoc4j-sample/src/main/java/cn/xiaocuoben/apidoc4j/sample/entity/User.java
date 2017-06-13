package cn.xiaocuoben.apidoc4j.sample.entity;

import lombok.Data;

/**
 * 用户类
 * @author Frank
 * @date 2017-05-10
 */
@Data
public class User {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 子用户
     */
    private User user;
}
