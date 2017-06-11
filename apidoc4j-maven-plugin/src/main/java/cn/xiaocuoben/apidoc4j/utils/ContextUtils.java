package cn.xiaocuoben.apidoc4j.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Frank
 * @date 2017-05-20
 */
public class ContextUtils {
    private ContextUtils(){}

    private static Map<String,String> CONTEXT_MAP = new HashMap<>();

    public static String get(Object key) {
        return CONTEXT_MAP.get(key);
    }

    public static String put(String key, String value) {
        return CONTEXT_MAP.put(key, value);
    }
}
