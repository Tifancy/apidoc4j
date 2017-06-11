package cn.xiaocuoben.apidoc4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MethodComment extends AbstractComment {
    /**
     * HTTP请求方法
     */
    private String requestMethod;

    private List<MethodArgumentComment> methodArgumentCommentList = new ArrayList<>();

    private MethodReturnComment methodReturnComment;
}
