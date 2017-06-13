## 用户接口


### 1.登录

```
POST,GET /user/login
```

参数：

```
{//测试
	"userName":"java.lang.String",//用户名
	"password":"java.lang.String",//密码
	"userLoginRequest":{//测试
		"userName":"java.lang.String",//用户名
		"password":"java.lang.String",//密码
		"userLoginRequest":{}
	}
}
```

返回值：

```
{//实际数据
	"username":"java.lang.String",//用户名
	"password":"java.lang.String",//密码
	"user":{//子用户
		"username":"java.lang.String",//用户名
		"password":"java.lang.String",//密码
		"user":{}
	}
}
```
