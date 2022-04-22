package net.katool.common.http;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * @author hongchen.cao
 * @since 22 四月 2021
 */
public class HttpClientFactory {
    public static CloseableHttpClient createHttpClient() {
        return httpClientBuilder().build();
    }

    private static HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager());
        httpClientBuilder.setRetryHandler(httpRequestRetryHandler());
        httpClientBuilder.setDefaultRequestConfig(requestConfig());
        return httpClientBuilder;
    }

    private static HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 设置整个连接池最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(200);
        // 路由是对maxTotal的细分
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
        //闲置连接超时回收
        poolingHttpClientConnectionManager.closeIdleConnections(10, TimeUnit.SECONDS);
        return poolingHttpClientConnectionManager;
    }

    private static HttpRequestRetryHandler httpRequestRetryHandler() {
        return new DefaultHttpRequestRetryHandler(3, true);
    }

    private static RequestConfig requestConfig() {
        return RequestConfig.custom()
                // 服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setSocketTimeout(100000)
                // 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectTimeout(5000)
                // 从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .setConnectionRequestTimeout(2000)
                .build();
    }
}
