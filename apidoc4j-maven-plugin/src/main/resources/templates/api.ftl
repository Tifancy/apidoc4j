<#list classCommentList as classComment>
##${classComment.comment!""}

<#list classComment.methodCommentList as methodComment>

###${methodComment_index + 1}.${methodComment.comment!""}

```
${methodComment.requestMethod} ${methodComment.uri}
```

参数：

```
{
<#list methodComment.methodArgumentCommentList as argumentComment>
    <#list argumentComment.fieldCommentList as fieldComment>
    "${fieldComment.name!""}":${fieldComment.typeName}<#if (fieldComment_has_next)>,</#if>//${fieldComment.comment!""}
    </#list>
</#list>
}
```

返回值：

```
{
<#list methodComment.methodReturnComment.fieldCommentList as fieldComment>
    "${fieldComment.name!""}":${fieldComment.typeName}<#if (fieldComment_has_next)>,</#if>//${fieldComment.comment!""}
</#list>
}
```
</#list>
</#list>