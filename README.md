#### 这是一个生成API文档的maven插件
用法

项目暂时还没有上传到中央仓库需要先执行下面的几个命令，把apidoc4j安装到本地

```
git clone https://github.com/liuyuyu/apidoc4j
cd apidoc4j
mvn clean install -Dmaven.test.skip=true
```
然后创建自己的工程

在pom.xml中增加
```
<plugin>
    <groupId>cn.xiaocuoben</groupId>
    <artifactId>apidoc4j-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <basePackage>cn.xiaocuoben.apidoc4j.sample</basePackage>
        <output>${project.basedir}/apidoc.md</output>
        <resources>
            <resource>${project.build.sourceDirectory}</resource>
            <resource>${project.parent.basedir}\apidoc4j-sample-common\src\main\java</resource>
        </resources>
    </configuration>
</plugin>
```
然后执行
```
mvn api4j:generate
```

就会在项目的根目录生成apidoc.md文件

支持框架：SpringMVC

生成格式

[生成结果示例](/apidoc4j-sample/apidoc4j-sample-gateway/apidoc.md)

截图

![参数](/apidoc4j-sample/apidoc4j-sample-gateway/snapshot/canshu.png)
![返回值](/apidoc4j-sample/apidoc4j-sample-gateway/snapshot/fanhuizhi.png)


## 特性

* [x] maven插件
* [x] springMVC支持
* [x] JSON支持
* [x] 生成markdown