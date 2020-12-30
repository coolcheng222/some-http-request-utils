package com.sealll.beanutils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sealll
 * @describe
 * @time 2020/12/26 21:26
 */
public class BeanPropertiesUtils {
    public static<T> void beanReflect(T bean,KeyValueConsumer kv ) throws InvocationTargetException, IllegalAccessException {
        if(bean == null){
            return ;
        }
        Class<?> clazz = bean.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        String key;
        String value;
        for (Method declaredMethod : declaredMethods) {
            if(declaredMethod.getName().startsWith("get")){
                key = lowerFirst(declaredMethod.getName().substring(3));
                value = (String) declaredMethod.invoke(bean);
                if(value != null){
                    kv.dealing(key,value);
                }
            }
        }
    }
    private static String lowerFirst(String s){
        if(s == null){
            return null;
        }
        return s.substring(0,1).toLowerCase() + s.substring(1);
    }
}
