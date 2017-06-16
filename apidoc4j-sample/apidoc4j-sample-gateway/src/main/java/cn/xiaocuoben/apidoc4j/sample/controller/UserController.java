package cn.xiaocuoben.apidoc4j.sample.controller;

import cn.xiaocuoben.apidoc4j.sample.model.entity.User;
import cn.xiaocuoben.apidoc4j.sample.request.UserLoginRequest;
import cn.xiaocuoben.apidoc4j.sample.response.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户接口
 * @author Frank
 * @date 2017-05-21
 */
@Controller
@RequestMapping(value = "/user",method = {RequestMethod.POST,RequestMethod.PUT})
public class UserController {
    /**
     * 登录
     */
    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseWrapper<User> login(UserLoginRequest userLoginRequest){
        ResponseWrapper<User> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setIsSuccess(Boolean.TRUE);
        User user = new User();
        user.setUsername("zs");
        responseWrapper.setData(user);
        responseWrapper.setMsg("登录成功");
        return responseWrapper;
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public void upload(MultipartFile[] files){

    }

    private Object cc(UserLoginRequest userLoginRequest){
        return new Object();
    }
}
