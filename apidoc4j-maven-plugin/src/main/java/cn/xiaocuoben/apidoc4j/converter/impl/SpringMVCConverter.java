package cn.xiaocuoben.apidoc4j.converter.impl;

import cn.xiaocuoben.apidoc4j.converter.Converter;
import cn.xiaocuoben.apidoc4j.model.*;
import cn.xiaocuoben.apidoc4j.render.Filler;
import cn.xiaocuoben.apidoc4j.utils.FreemarkerRenderUtils;
import com.sun.javadoc.*;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.xiaocuoben.apidoc4j.utils.DocUtils.*;

/**
 * @author Frank
 * @date 2017-05-20
 */
public class SpringMVCConverter implements Converter {

    @Override
    public void convert(RootDoc rootDoc) throws IOException, TemplateException {
        List<ClassDoc> classDocList = findValidClass(rootDoc, Controller.class, RestController.class);
        List<ClassComment> classCommentList = new ArrayList<>();

        for (ClassDoc classDoc : classDocList) {
            ClassComment classComment = new ClassComment();
            classComment.setComment(classDoc.commentText());
            classComment.setRawComment(classComment.getRawComment());

            RequestMapping controllerRequestMapping = findClassAnnotation(classDoc, RequestMapping.class);

            //METHOD
            List<MethodComment> methodCommentList = new ArrayList<>();
            List<MethodDoc> methodDocList = findValidMethod(classDoc, RequestMapping.class);

            for (MethodDoc methodDoc : methodDocList) {
                MethodComment methodComment = new MethodComment();
                methodComment.setComment(methodDoc.commentText());
                methodComment.setRawComment(methodDoc.getRawCommentText());

                RequestMapping requestMapping = findMethodAnnotation(classDoc, methodDoc, RequestMapping.class);
                methodComment.setUri(controllerRequestMapping.value()[0] + requestMapping.value()[0]);
                StringBuilder methodBuilder = new StringBuilder();
                if (requestMapping.method().length > 0) {
                    for (RequestMethod requestMethod : requestMapping.method()) {
                        methodBuilder.append(requestMethod.name()).append(",");
                    }
                    methodBuilder.deleteCharAt(methodBuilder.length() - 1);
                } else {
                    methodBuilder.append("GET");
                }
                methodComment.setRequestMethod(methodBuilder.toString());

                //参数
                FieldComment methodArgumentComment = new FieldComment();

                for (Parameter parameter : methodDoc.parameters()) {
                    FieldComment parameterFieldComment = new FieldComment();
                    List<FieldComment> fieldCommentList = convertToFieldCommentList(rootDoc, parameter);
                    parameterFieldComment.setFieldCommentList(fieldCommentList);

                    methodArgumentComment.getFieldCommentList().add(parameterFieldComment);
                }
                methodComment.setMethodArgumentComment(methodArgumentComment);

                //返回值
                List<FieldComment> fieldCommentList = convertToFieldComment(rootDoc, methodDoc.returnType(), 5, 0);

                FieldComment methodReturnComment = new FieldComment();
                methodReturnComment.setFieldCommentList(fieldCommentList);

                methodComment.setMethodReturnComment(methodReturnComment);

                methodCommentList.add(methodComment);
            }
            classComment.setMethodCommentList(methodCommentList);
            classCommentList.add(classComment);
        }

        Filler filler = new Filler();
        filler.fillClass(classCommentList);
        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("classCommentList", classCommentList);
        FreemarkerRenderUtils.render(commentMap);
    }

}
