package cn.xiaocuoben.apidoc4j.converter;

import com.sun.javadoc.RootDoc;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Frank
 * @date 2017-05-20
 */
public interface Converter {
    void convert(RootDoc rootDoc) throws IOException, TemplateException;
}
