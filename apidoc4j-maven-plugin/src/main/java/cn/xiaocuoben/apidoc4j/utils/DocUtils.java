package cn.xiaocuoben.apidoc4j.utils;

import cn.xiaocuoben.apidoc4j.model.FieldComment;
import com.sun.javadoc.*;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank
 * @version 2017/6/12
 */
public class DocUtils {
    private DocUtils() {
    }

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
            List<FieldComment> classFieldCommentList = convertToFieldComment(rootDoc, parameterClassDoc, 5, 0);
            fieldCommentList.addAll(classFieldCommentList);
        }
        return fieldCommentList;
    }

    /**
     * 递归获取所有属性，超过递归次数之后停止继续获取
     *
     * @param times 当前次数
     * @param limit 递归次数限制
     */
    public static List<FieldComment> convertToFieldComment(RootDoc rootDoc, Type returnType, int limit, int times) {
        if (times > limit) {
            return null;
        }

        List<FieldComment> fieldCommentList = new ArrayList<>();
        Map<String, Type> parameterizedTypeMap = new HashMap<>();

        if (returnType.asParameterizedType() != null) {
            Type[] typeArguments = returnType.asParameterizedType().typeArguments();
            TypeVariable<? extends Class<?>>[] typeParameters = ReflectionUtils.forName(returnType.qualifiedTypeName()).getTypeParameters();
            for (int i = 0; i < typeArguments.length; i++) {
                parameterizedTypeMap.put(typeParameters[i].getName(), typeArguments[i]);
            }
        }

        for (FieldDoc fieldDoc : returnType.asClassDoc().fields(false)) {
            Type fieldType = parameterizedTypeMap.get(fieldDoc.type().qualifiedTypeName());
            if(fieldType == null){
                fieldType = fieldDoc.type();
            }
            List<ClassDoc> classDocList = findValidClass(rootDoc, fieldType.qualifiedTypeName());

            FieldComment fieldComment = new FieldComment();
            fieldComment.setComment(fieldDoc.commentText());
            fieldComment.setRawComment(fieldDoc.getRawCommentText());
            fieldComment.setName(fieldDoc.name());

            Type parameterizedType = parameterizedTypeMap.get(fieldDoc.type().qualifiedTypeName());
            String typeName;
            if (parameterizedType == null) {
                typeName = fieldDoc.type().qualifiedTypeName();
            } else {
                typeName = parameterizedType.qualifiedTypeName();
                classDocList.add(parameterizedType.asClassDoc());
            }
            fieldComment.setTypeName(typeName);

            for (ClassDoc doc : classDocList) {
                List<FieldComment> subFieldCommentList = convertToFieldComment(rootDoc, doc, limit, ++times);
                fieldComment.setFieldCommentList(subFieldCommentList);
            }
            fieldCommentList.add(fieldComment);
        }
        return fieldCommentList;
    }

    @SuppressWarnings("unchecked")
    public static <T> T findMethodAnnotation(ClassDoc classDoc, MethodDoc methodDoc, Class annotationClass) {
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
    public static <T> T findClassAnnotation(ClassDoc classDoc, Class annotationClass) {
        Class controllerClazz = ReflectionUtils.forName(classDoc.qualifiedTypeName());
        Annotation annotation = controllerClazz.getAnnotation(annotationClass);
        return (T) annotation;
    }
}
