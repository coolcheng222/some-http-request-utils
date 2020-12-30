package com.sealll.beanutils;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sealll
 * @describe
 * @time 2020/11/22 10:10
 */
public class BeanParamUtils {
    private static final Gson gson = new Gson();
    public static<T> UrlEncodedFormEntity getEntity(T bean) throws UnsupportedEncodingException, InvocationTargetException, IllegalAccessException {
        if(bean == null){
            return null;
        }
        List<NameValuePair> list = new ArrayList<>();
        BeanPropertiesUtils.beanReflect(bean,(key,value)->{
            list.add(new BasicNameValuePair(key,value.toString()));
        });
        return new UrlEncodedFormEntity(list);

    }
    public static UrlEncodedFormEntity getEntity(Map<String,String> map) throws UnsupportedEncodingException {
        if(map == null){
            return null;
        }
        List<NameValuePair> res = new ArrayList<>();
        for ( Map.Entry<String,String> entry: map.entrySet()) {
            res.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        return new UrlEncodedFormEntity(res);
    }
    public static<T>StringEntity getEntity(T bean, Boolean isJson){
        return new StringEntity(gson.toJson(bean), ContentType.APPLICATION_JSON);
    }
    public static StringEntity getEntity(Map<String,String> map,Boolean isJson){
        return new StringEntity(gson.toJson(map), ContentType.APPLICATION_JSON);
    }
    public static Header[] getHeaders(Map<String,String> map){
        if(map == null){
            return new Header[0];
        }
        List<Header> res = new ArrayList<>();
        for ( Map.Entry<String,String> entry: map.entrySet()) {
            res.add(new BasicHeader(entry.getKey(),entry.getValue()));
        }
        return res.toArray(new Header[0]);
    }
    public static<T> CookieStore getCookies(T bean,String domain) throws InvocationTargetException, IllegalAccessException {
        CookieStore cs;
        if (bean == null) {
            return null;
        } else {
            cs = new BasicCookieStore();
            BeanPropertiesUtils.beanReflect(bean, ((key, value) -> {
                BasicClientCookie cookie = new BasicClientCookie(key, value.toString());
                cookie.setDomain(domain);
                cs.addCookie(cookie);
            }));
        }
        return cs;

    }

    private static String lowerFirst(String s){
        if(s == null){
            return null;
        }
        return s.substring(0,1).toLowerCase() + s.substring(1);
    }
}
