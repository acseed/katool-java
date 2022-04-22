package net.katool.common.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;
import javax.annotation.Nonnull;

/**
 * @author hongchen.cao
 * @since 22 四月 2021
 */
public class HttpComponentsClientHttpRequestWithBodyFactory extends HttpComponentsClientHttpRequestFactory {
    public HttpComponentsClientHttpRequestWithBodyFactory(HttpClient httpClient) {
        super(httpClient);
    }

    @Nonnull
    @Override
    protected HttpUriRequest createHttpUriRequest(@Nonnull HttpMethod httpMethod, @Nonnull URI uri) {
        if (httpMethod == HttpMethod.GET) {
            return new HttpGetRequestWithEntity(uri);
        }
        return super.createHttpUriRequest(httpMethod, uri);
    }
}

