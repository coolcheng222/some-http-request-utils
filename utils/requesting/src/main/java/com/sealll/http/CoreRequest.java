package com.sealll.http;

import com.sealll.beanutils.BeanParamUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author sealll
 * @describe
 * @time 2020/12/26 20:47
 */
public class CoreRequest {
    private static HttpClientBuilder hb = HttpClientBuilder.create();
    static String getString(HttpClientContext context, String url, String method, Map<String, String> headers, HttpEntity entity2) throws InvocationTargetException, IllegalAccessException, IOException {
        try (CloseableHttpResponse response = getResponse(context, url, method, headers, entity2)) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        }
    }

    static HttpEntity getEntities(HttpClientContext context, String url, String method, Map<String, String> headers, HttpEntity entity2) throws IOException {
        try (CloseableHttpResponse response = getResponse(context, url, method, headers, entity2)) {
            return response.getEntity();
        }
    }

    static CloseableHttpResponse getWholeResponse(HttpClientContext context, String url, String method, Map<String, String> headers, HttpEntity entity2) throws IOException {
        return getResponse(context, url, method, headers, entity2);
    }

    private static CloseableHttpResponse getResponse(HttpClientContext context, String url, String method, Map<String, String> headers, HttpEntity entity2) throws IOException {
        try (CloseableHttpClient client = hb.build()) {
            CloseableHttpResponse execute = null;

            Header[] headers1 = BeanParamUtils.getHeaders(headers);
            if (method.equalsIgnoreCase("POST")) {
                HttpPost post = new HttpPost(url);
                post.setHeaders(headers1);
                post.setEntity(entity2);
                execute = client.execute(post, context);
            } else {
                HttpGet get = new HttpGet(url);
                get.setHeaders(headers1);

                execute = client.execute(get, context);

            }
//            return EntityUtils.toString(execute.getEntity(), StandardCharsets.UTF_8);
            return execute;
        }
    }
}
