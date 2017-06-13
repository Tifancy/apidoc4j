package cn.xiaocuoben.apidoc4j.doclet;

import cn.xiaocuoben.apidoc4j.converter.ConverterManager;
import com.sun.javadoc.*;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import com.sun.tools.doclets.standard.Standard;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Frank
 */
public class APIDocDoclet extends Standard {


    public static boolean start(RootDoc root) {
        try {
            ConverterManager.getConverter().convert(root);
        } catch (IOException | TemplateException e) {
            return false;
        }
        return true;
    }

    /**
     * 检查选项
     * @param option 附加选项
     */
    public static int optionLength(String option) {
        return option.split(" ").length;
    }

    /**
     * 验证参数有效性
     * @param options 参数
     * @param reporter 参数报告器
     */
    public static boolean validOptions(String options[][], DocErrorReporter reporter) {
        return true;
    }
}