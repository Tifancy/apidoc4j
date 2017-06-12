package cn.xiaocuoben.apidoc4j.utils;

import cn.xiaocuoben.apidoc4j.model.FieldComment;
import com.sun.javadoc.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank
 * @version 2017/6/12
 */
public class DocUtils {
    private DocUtils(){}

    public static List<ClassDoc> findValidClass(RootDoc rootDoc, String clazzName) {
        List<ClassDoc> classDocList = new ArrayList<>();
        for (ClassDoc classDoc : rootDoc.classes()) {
            if (classDoc.qualifiedTypeName().equals(clazzName)) {
                classDocList.add(classDoc);
            }
        }
        return classDocList;
    }

    public static List<ClassDoc> findValidClass(RootDoc rootDoc, Class... annotationClazzs) {
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

    public static List<MethodDoc> findValidMethod(ClassDoc classDoc, Class... annotationClazzs) {
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

    public static List<FieldComment> convertToFieldCommentList(RootDoc rootDoc, Parameter parameter) {
        List<FieldComment> fieldCommentList = new ArrayList<>();

        List<ClassDoc> parameterClassDocList = findValidClass(rootDoc, parameter.type().qualifiedTypeName());
        for (ClassDoc parameterClassDoc : parameterClassDocList) {
            List<FieldComment> classFieldCommentList = convertToFieldComment(parameterClassDoc);
            fieldCommentList.addAll(classFieldCommentList);
        }
        return fieldCommentList;
    }

    public static List<FieldComment> convertToFieldComment(ClassDoc classDoc) {
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

    @SuppressWarnings("unchecked")
    public static <T> T findMethodAnnotation(ClassDoc classDoc,MethodDoc methodDoc, Class annotationClass){
        Class controllerClazz = ReflectionUtils.forName(classDoc.qualifiedTypeName());
        Parameter[] parameterArray = methodDoc.parameters();
        Class[] parameterClassArray = new Class[parameterArray.length];
        for (int i = 0; i < parameterArray.length; i++) {
            parameterClassArray[i] = ReflectionUtils.forName(parameterArray[i].type().qualifiedTypeName());
        }

        Method method = ReflectionUtils.findMethod(controllerClazz, methodDoc.name(), parameterClassArray);
        Annotation annotation = method.getAnnotation(annotationClass);
        return (T) annotation;
    }

    @SuppressWarnings("unchecked")
    public static <T> T findClassAnnotation(ClassDoc classDoc,Class annotationClass){
        Class controllerClazz = ReflectionUtils.forName(classDoc.qualifiedTypeName());
        Annotation annotation = controllerClazz.getAnnotation(annotationClass);
        return (T) annotation;
    }
}
