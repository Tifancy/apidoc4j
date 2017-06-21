#### 这是一个生成API文档的maven插件
用法

在pom.xml中增加
```
<plugin>
    <groupId>cn.xiaocuoben</groupId>
    <artifactId>apidoc4j-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <basePackage>cn.xiaocuoben.apidoc4j.sample</basePackage>
        <output>${project.basedir}/apidoc.md</output>
    </configuration>
</plugin>
```
然后执行
```
mvn api4j:generate
```

支持框架：SpringMVC

生成格式

[生成结果示例](/apidoc4j-sample/apidoc4j-sample-gateway/apidoc.md)

## 特性

* [x] maven插件
* [x] springMVC支持
* [x] JSON支持
* [x] 生成markdown