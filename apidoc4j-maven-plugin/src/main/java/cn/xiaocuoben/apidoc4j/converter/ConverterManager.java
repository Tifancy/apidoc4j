package cn.xiaocuoben.apidoc4j.converter;

import cn.xiaocuoben.apidoc4j.converter.impl.SpringMVCConverter;

/**
 * @author Frank
 * @date 2017-05-20
 */
public class ConverterManager {
    private ConverterManager(){}

    public static Converter getConverter(){
        return new SpringMVCConverter();
    }
}
