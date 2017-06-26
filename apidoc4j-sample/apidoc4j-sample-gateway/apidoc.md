## 用户接口


### 1.登录

```
POST,GET /user/login
```

参数：

```
[
	{//
		"userName":"java.lang.String",//用户名
		"password":"java.lang.String",//密码
		"userLoginRequest":{//测试
			"userName":"java.lang.String",//用户名
			"password":"java.lang.String",//密码
			"userLoginRequest":{//测试
				"userName":"java.lang.String",//用户名
				"password":"java.lang.String",//密码
				"userLoginRequest":{//测试
					"userName":"java.lang.String",//用户名
					"password":"java.lang.String",//密码
					"userLoginRequest":{//测试
						"userName":"java.lang.String",//用户名
						"password":"java.lang.String",//密码
						"userLoginRequest":{//测试
							"userName":"java.lang.String",//用户名
							"password":"java.lang.String",//密码
							"userLoginRequest":{}
						}
					}
				}
			}
		}
	}
]
```

返回值：

```
{//
		"isSuccess":"java.lang.Boolean",//是否成功
		"msg":"java.lang.String",//提示信息
		"data":{//实际数据
			"username":"java.lang.String",//用户名
			"password":"java.lang.String",//密码
			"user":{//子用户
				"username":"java.lang.String",//用户名
				"password":"java.lang.String",//密码
				"user":{//子用户
					"username":"java.lang.String",//用户名
					"password":"java.lang.String",//密码
					"user":{//子用户
						"username":"java.lang.String",//用户名
						"password":"java.lang.String",//密码
						"user":{}
						"photoList":{//用户照片
						}
					}
					"photoList":{//用户照片
					}
				}
				"photoList":{//用户照片
				}
			}
			"photoList":{//用户照片
			}
		}
	}

```
