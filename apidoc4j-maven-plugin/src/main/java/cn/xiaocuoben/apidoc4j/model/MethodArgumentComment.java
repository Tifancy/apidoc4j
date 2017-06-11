package cn.xiaocuoben.apidoc4j.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank
 */
@Data
public class MethodArgumentComment{

    private List<FieldComment> fieldCommentList = new ArrayList<>();
}
