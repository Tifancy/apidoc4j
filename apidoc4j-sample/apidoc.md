## 用户接口


### 1.登录

```
POST,GET /user/login
```

参数：

```
{
    "userName":String,//用户名
    "password":String,//密码
    "userLoginRequest":UserLoginRequest//测试
}
```

返回值：

```
{
    "isSuccess":Boolean,//是否成功
    "msg":String,//提示信息
    "data":Object//实际数据
}
```
