package com.sealll.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sealll.beanutils.BeanParamUtils;
import org.apache.http.client.protocol.HttpClientContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author sealll
 * @describe
 * @time 2020/11/22 20:37
 */
public class HttpRequestUtils {
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    /**
     *
     * @param context context
     * @param headers headers
     * @param params request params
     * @param <T>
     * @return
     */
    public static <T> String sendFormForText(HttpClientContext context, String url,
                                                String method, Map<String,String> headers,
                                                T params) throws IllegalAccessException, IOException, InvocationTargetException {
        return unifiedRequest(context,url,method,headers,params);

    }
    public static<T> JsonElement sendFormForJson(HttpClientContext context,String url,
                                                  String method, Map<String,String> headers,
                                                 T params) throws IllegalAccessException, IOException, InvocationTargetException {
        String s = unifiedRequest(context, url, method, headers, params,true);
        return parser.parse(s);
    }

    public static <T> String sendJsonForText(HttpClientContext context,String url,
                                             String method, Map<String,String> headers,
                                             T params) throws IllegalAccessException, IOException, InvocationTargetException {
        return unifiedRequest(context,url,method,headers,params);

    }

    public static<T> JsonElement sendJsonForJson(HttpClientContext context, String url,
                                                 String method, Map<String,String> headers,
                                                 T params) throws IllegalAccessException, IOException, InvocationTargetException {
        String s = unifiedRequest(context, url, method, headers, params,true);
        return parser.parse(s);
    }


    private static <T> String unifiedRequest(HttpClientContext context, String url,
                                             String method, Map<String,String> headers,
                                             T params,boolean isJson) throws IllegalAccessException, IOException, InvocationTargetException {

        return CoreRequest.getString(context, url, method, headers, BeanParamUtils.getEntity(params,isJson));
    }


    private static <T> String unifiedRequest(HttpClientContext context,String url,
                                                String method, Map<String,String> headers,
                                                T params) throws IllegalAccessException, IOException, InvocationTargetException {
        return CoreRequest.getString(context, url, method, headers, BeanParamUtils.getEntity(params));
    }


}
