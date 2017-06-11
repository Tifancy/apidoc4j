package cn.xiaocuoben.apidoc4j.model;

import lombok.Data;

/**
 * @author Frank
 */
@Data
public abstract class AbstractComment {
    /**
     * innerText
     */
    private String comment;
    /**
     * innerHTML
     */
    private String rawComment;
}
