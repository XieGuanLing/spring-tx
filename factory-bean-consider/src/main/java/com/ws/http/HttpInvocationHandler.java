package com.ws.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * create by gl
 * on 2018/5/2
 */
public class HttpInvocationHandler implements InvocationHandler {


    // HttpClient
    private CloseableHttpClient httpClient;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 初始化 HttpClient
     */
    public HttpInvocationHandler() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 设置总的最大连接数
        connectionManager.setMaxTotal(500);
        // 设置单机最大连接数
        connectionManager.setDefaultMaxPerRoute(100);

        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

    }

    /**
     * 需要处理异常
     *
     * 代理方法，执行 http 请求。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        HttpUriRequest httpRequest = buildHttpRequest(method, args);
        if (httpRequest == null) {
        }

        CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {

            }
            HttpEntity entity = httpResponse.getEntity();
            Object response = null;
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                try {
                    response = objectMapper.readValue(new InputStreamReader(inputStream), method.getReturnType());
                } finally {
                    inputStream.close();
                }
            }
            return response;


    }



    /**
     * 构造 Http 请求。
     */
    private HttpUriRequest buildHttpRequest(Method method, Object[] args) {
        HttpInvoker invokerMethod = method.getAnnotation(HttpInvoker.class);
        if (invokerMethod == null) {
            return null;
        }
        if (args == null || args.length == 0) {
            return null;
        }
        Object requestObject = args[0];
        String jsonRequest = null;
        try {
            jsonRequest = objectMapper.writeValueAsString(requestObject);
        } catch (JsonProcessingException e) {

        }
        HttpUriRequest httpUriRequest;
        switch (invokerMethod.method()) {
            case GET:
                httpUriRequest = createGetRequest(invokerMethod, jsonRequest);
                break;
            case POST:
                httpUriRequest = createPostRequest(invokerMethod, jsonRequest);
                break;
            default:
                httpUriRequest = null;
                break;
        }
        return httpUriRequest;
    }

    /**
     * 创建加密 Get 请求。
     */
    private HttpUriRequest createGetRequest(HttpInvoker method, String json) {
        URI uri;
        try {
            uri = new URIBuilder()
                    .setScheme(method.schema())
                    .setHost(method.host())
                    .setPort(method.port())
                    .setPath(method.path())
                    .addParameter("data", json)
                    .build();
        } catch (URISyntaxException e) {
            return null;
        }
        RequestConfig config = RequestConfig.custom().setSocketTimeout(method.timeout()).build();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(config);
        return httpGet;
    }


    private HttpUriRequest createPostRequest(HttpInvoker method, String json) {
        URI uri;
        try {
            uri = new URIBuilder()
                    .setScheme(method.schema())
                    .setHost(method.host())
                    .setPort(method.port())
                    .setPath(method.path())
                    .build();
        } catch (URISyntaxException e) {
            return null;
        }
        RequestConfig config = RequestConfig.custom().setSocketTimeout(method.timeout()).build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(config);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

        if(json != null){
            StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
            s.setContentType("application/json");
            httpPost.setEntity(s);
        }

        return httpPost;
    }

}
