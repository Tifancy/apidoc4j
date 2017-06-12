package cn.xiaocuoben.apidoc4j.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 刘玉雨
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtils {

    public static Class<?> forName(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method findMethod(Class<?> clazz, String methodName, Class... argumentTypeNames) {
        return org.springframework.util.ReflectionUtils.findMethod(clazz,methodName,argumentTypeNames);
    }

    public static Field findField(Class<?> clazz, String fieldName){
        return org.springframework.util.ReflectionUtils.findField(clazz,fieldName);
    }

    public static Annotation findAnnotation(String className, String methodName, Class annotationClass, Class... argumentTypeNames){
        Class<?> clazz = ReflectionUtils.forName(className);
        Method method = ReflectionUtils.findMethod(clazz, methodName, argumentTypeNames);
        for (Annotation annotation : method.getAnnotations()) {
            if(annotation.getClass().equals(annotationClass)){
                return annotation;
            }
        }
        return null;
    }

    public static Field findAnnotationField(String className, String methodName, String fieldName, Class annotationClass, Class... argumentTypeNames){
        Annotation annotation = findAnnotation(className, methodName, annotationClass, argumentTypeNames);
        return findField(annotation.annotationType(),fieldName);
    }
}
