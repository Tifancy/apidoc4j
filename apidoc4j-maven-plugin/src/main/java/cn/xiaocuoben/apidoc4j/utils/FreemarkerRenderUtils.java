package cn.xiaocuoben.apidoc4j.utils;

import cn.xiaocuoben.apidoc4j.constant.API4jConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;

/**
 * @author Frank
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FreemarkerRenderUtils {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_23);
    public static Writer OUT;
    public static final String TEMPLATE_NAME = "api.ftl";

    static {
        CONFIGURATION.setDefaultEncoding(DEFAULT_ENCODING);
        CONFIGURATION.setClassLoaderForTemplateLoading(FreemarkerRenderUtils.class.getClassLoader(),"templates");
    }

    public static void render(Object model) throws IOException, TemplateException {
        Template template = CONFIGURATION.getTemplate(TEMPLATE_NAME);
        template.process(model,OUT);
    }
}
