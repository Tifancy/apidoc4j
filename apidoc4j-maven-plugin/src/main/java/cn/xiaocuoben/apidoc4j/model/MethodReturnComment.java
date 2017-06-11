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
public class MethodReturnComment extends AbstractComment {

    private List<FieldComment> fieldCommentList = new ArrayList<>();
}
