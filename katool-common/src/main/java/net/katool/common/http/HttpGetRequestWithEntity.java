package net.katool.common.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.springframework.http.HttpMethod;

import java.net.URI;

/**
 * @author hongchen.cao
 * @since 22 四月 2021
 */
public class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
    public HttpGetRequestWithEntity(final URI uri) {
        super.setURI(uri);
    }

    @Override
    public String getMethod() {
        return HttpMethod.GET.name();
    }
}