package cn.xiaocuoben.apidoc4j.sample.response;

import lombok.Data;

/**
 * @author Frank
 * @date 2017-05-21
 */
@Data
public class ResponseWrapper<T> {
    private Boolean isSuccess;
    private String msg;
    private T data;
}
