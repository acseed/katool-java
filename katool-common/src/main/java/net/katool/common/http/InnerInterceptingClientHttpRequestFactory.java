package net.katool.common.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;

import java.net.URI;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * @author hongchen.cao
 * @since 07 四月 2022
 */
public class InnerInterceptingClientHttpRequestFactory extends InterceptingClientHttpRequestFactory {
    public InnerInterceptingClientHttpRequestFactory(ClientHttpRequestFactory requestFactory, 
                                                     List<ClientHttpRequestInterceptor> interceptors) {
        super(requestFactory, interceptors);
    }

    @Nonnull
    @Override
    protected ClientHttpRequest createRequest(@Nonnull URI uri,
                                              @Nonnull HttpMethod httpMethod,
                                              @Nonnull ClientHttpRequestFactory requestFactory) {
        return super.createRequest(uri, httpMethod, requestFactory);
    }
}
