package net.katool.common.http.invoker;

import org.springframework.core.ParameterizedTypeReference;

/**
 * @author hongchen.cao
 * @since 22 四月 2022
 */
public interface HttpInvoker {
    <R, Q> Q httpPostJson(String url, R payload, Class<Q> responseType);

    <Q> Q httpGet(String url, Class<Q> responseType);

    <R, Q> Q httpPostJson(String url, R payload, ParameterizedTypeReference<Q> typeReference);

    <Q> Q httpGet(String url, ParameterizedTypeReference<Q> typeReference);
}
