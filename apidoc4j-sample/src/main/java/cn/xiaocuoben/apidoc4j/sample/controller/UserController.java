package cn.xiaocuoben.apidoc4j.sample.controller;

import cn.xiaocuoben.apidoc4j.sample.entity.User;
import cn.xiaocuoben.apidoc4j.sample.request.UserLoginRequest;
import cn.xiaocuoben.apidoc4j.sample.response.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户接口
 * @author Frank
 * @date 2017-05-21
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseWrapper login(UserLoginRequest userLoginRequest){
        ResponseWrapper responseWrapper = new ResponseWrapper<User>();
        responseWrapper.setIsSuccess(Boolean.TRUE);
        User user = new User();
        user.setUsername("zs");
        responseWrapper.setData(user);
        responseWrapper.setMsg("登录成功");
        return responseWrapper;
    }

    private Object cc(UserLoginRequest userLoginRequest){
        return new Object();
    }
}
