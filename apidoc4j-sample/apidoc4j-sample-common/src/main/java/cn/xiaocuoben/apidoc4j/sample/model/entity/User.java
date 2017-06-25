package cn.xiaocuoben.apidoc4j.sample.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    /**
     * 用户照片
     */
    private List<Photo> photoList = new ArrayList<>();
}
