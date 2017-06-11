package cn.xiaocuoben.apidoc4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Frank
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FieldComment extends AbstractComment {
    /**
     * 属性名
     */
    private String name;
    /**
     * 属性类型
     */
    private String typeName;
}
