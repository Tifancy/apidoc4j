package cn.xiaocuoben.apidoc4j.converter.impl;

import cn.xiaocuoben.apidoc4j.converter.Converter;
import cn.xiaocuoben.apidoc4j.model.*;
import cn.xiaocuoben.apidoc4j.utils.FreemarkerRenderUtils;
import com.sun.javadoc.*;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank
 * @date 2017-05-20
 */
public class SpringMVCConverter implements Converter {

    @Override
    public void convert(RootDoc rootDoc) throws IOException, TemplateException {
        List<ClassDoc> classDocList = this.findValidClass(rootDoc, Controller.class, RestController.class);
        List<ClassComment> classCommentList = new ArrayList<>();

        for (ClassDoc classDoc : classDocList) {
            ClassComment classComment = new ClassComment();
            classComment.setComment(classDoc.commentText());
            classComment.setRawComment(classComment.getRawComment());

            //METHOD
            List<MethodComment> methodCommentList = new ArrayList<>();
            List<MethodDoc> methodDocList = this.findValidMethod(classDoc, RequestMapping.class);

            MethodComment methodComment = new MethodComment();
//            methodComment.setComment(parameterDoc.commentText());
//            methodComment.setRawComment(parameterDoc.getRawCommentText());

            for (MethodDoc methodDoc : methodDocList) {
                AnnotationDesc requestMappingAnnotationDesc = this.findAnnotation(methodDoc, RequestMapping.class);
                String method = "GET";
                if (requestMappingAnnotationDesc != null) {
                    requestMappingAnnotationDesc.annotationType();
                }
                methodComment.setRequestMethod(method);

                //参数
                List<MethodArgumentComment> methodArgumentCommentList = new ArrayList<>();
                MethodArgumentComment methodArgumentComment = new MethodArgumentComment();

                for (Parameter parameter : methodDoc.parameters()) {
                    List<FieldComment> fieldCommentList = this.convertToFieldCommentList(rootDoc, parameter);
                    methodArgumentComment.setFieldCommentList(fieldCommentList);
                    methodArgumentCommentList.add(methodArgumentComment);
                }
                methodComment.setMethodArgumentCommentList(methodArgumentCommentList);
                //返回值
                ClassDoc returnClassDoc = methodDoc.returnType().asClassDoc();
                List<FieldComment> fieldCommentList = this.convertToFieldComment(returnClassDoc);

                MethodReturnComment methodReturnComment = new MethodReturnComment();
                methodReturnComment.setFieldCommentList(fieldCommentList);

                methodComment.setMethodReturnComment(methodReturnComment);

                methodCommentList.add(methodComment);
            }
            classComment.setMethodCommentList(methodCommentList);
            classCommentList.add(classComment);
        }

        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("classCommentList", classCommentList);
        FreemarkerRenderUtils.render(commentMap);
    }

    public List<ClassDoc> findValidClass(RootDoc rootDoc, String clazzName) {
        List<ClassDoc> classDocList = new ArrayList<>();
        for (ClassDoc classDoc : rootDoc.classes()) {
            if (classDoc.qualifiedTypeName().equals(clazzName)) {
                classDocList.add(classDoc);
            }
        }
        return classDocList;
    }

    public List<ClassDoc> findValidClass(RootDoc rootDoc, Class... annotationClazzs) {
        List<ClassDoc> classDocList = new ArrayList<>();
        for (ClassDoc classDoc : rootDoc.classes()) {
            for (AnnotationDesc annotationDesc : classDoc.annotations()) {
                for (Class annotationClazz : annotationClazzs) {
                    if (annotationDesc.annotationType().qualifiedTypeName().equals(annotationClazz.getName())) {
                        classDocList.add(classDoc);
                    }
                }
            }
        }
        return classDocList;
    }

    public List<MethodDoc> findValidMethod(ClassDoc classDoc, Class... annotationClazzs) {
        List<MethodDoc> methodDocList = new ArrayList<>();
        for (MethodDoc methodDoc : classDoc.methods(false)) {
            for (AnnotationDesc methodAnnotationDesc : methodDoc.annotations()) {
                for (Class annotationClazz : annotationClazzs) {
                    if (methodAnnotationDesc.annotationType().qualifiedTypeName().equals(annotationClazz.getTypeName())) {
                        methodDocList.add(methodDoc);
                    }
                }
            }
        }
        return methodDocList;
    }

    public AnnotationDesc findAnnotation(ProgramElementDoc doc, Class annotationClazz) {
        for (AnnotationDesc annotationDesc : doc.annotations()) {
            if (annotationClazz.equals(annotationDesc.annotationType().qualifiedName())) {
                return annotationDesc;
            }
        }
        return null;
    }

    public List<FieldComment> convertToFieldCommentList(RootDoc rootDoc, Parameter parameter) {
        List<FieldComment> fieldCommentList = new ArrayList<>();

        List<ClassDoc> parameterClassDocList = this.findValidClass(rootDoc, parameter.type().qualifiedTypeName());
        for (ClassDoc parameterClassDoc : parameterClassDocList) {
            List<FieldComment> classFieldCommentList = this.convertToFieldComment(parameterClassDoc);
            fieldCommentList.addAll(classFieldCommentList);
        }
        return fieldCommentList;
    }

    public List<FieldComment> convertToFieldComment(ClassDoc classDoc) {
        List<FieldComment> fieldCommentList = new ArrayList<>();
        for (FieldDoc fieldDoc : classDoc.fields(false)) {
            FieldComment fieldComment = new FieldComment();
            fieldComment.setComment(fieldDoc.commentText());
            fieldComment.setRawComment(fieldDoc.getRawCommentText());
            fieldComment.setName(fieldDoc.name());
            fieldComment.setTypeName(fieldDoc.type().simpleTypeName());
            fieldCommentList.add(fieldComment);
        }
        return fieldCommentList;

    }

}
