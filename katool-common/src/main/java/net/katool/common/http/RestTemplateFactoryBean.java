package net.katool.common.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author hongchen.cao
 * @since 30 三月 2021
 */
@Getter
@Setter
public class RestTemplateFactoryBean implements FactoryBean<RestTemplate> {
    private RestTemplate restTemplate;
    
    private long connectionTimeout;
    private long readTimeout;
    private long writeTimeout;
    
    @Override
    public RestTemplate getObject() throws Exception {
        return createOne();
    }

    @Override
    public Class<?> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private RestTemplate createOne() {
        return new RestTemplate(requestFactory());
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        return new HttpComponentsClientHttpRequestWithBodyFactory(HttpClientFactory.createHttpClient());
    }
}
