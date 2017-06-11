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
public class ClassComment extends AbstractComment {
    /**
     * 方法注释
     */
    private List<MethodComment> methodCommentList = new ArrayList<>();
}
