package cn.xiaocuoben.apidoc4j.sample.request;

import lombok.Data;

/**
 * @author Frank
 * @date 2017-05-21
 */
@Data
public class UserLoginRequest {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
}
