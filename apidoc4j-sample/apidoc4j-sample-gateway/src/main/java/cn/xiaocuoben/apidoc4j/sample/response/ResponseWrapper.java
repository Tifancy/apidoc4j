package cn.xiaocuoben.apidoc4j.sample.response;

import lombok.Data;

/**
 * @author Frank
 * @date 2017-05-21
 */
@Data
public class ResponseWrapper<T> {
    /**
     * 是否成功
     */
    private Boolean isSuccess;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 实际数据
     */
    private T data;
}
