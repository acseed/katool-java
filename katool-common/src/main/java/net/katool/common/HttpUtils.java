package net.katool.common;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hongchen.cao
 * @since 22 四月 2022
 */
public class HttpUtils {
    public static Map<String, Object> queryStringToMap(String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return Maps.newHashMap();
        }
        Map<String, Object> queryParams = Maps.newHashMap();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            queryParams.put(keyValue[0], keyValue[1]);
        }
        return queryParams;
    }

    public static String mapToQueryString(Map<String, String> paramMap) {
        return paramMap.entrySet().stream()
                .filter(o -> StringUtils.isNotBlank(o.getValue()))
                .map(o -> o.getKey() + "=" + o.getValue())
                .collect(Collectors.joining("&"));
    }

    public static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlDecode(String string) {
        try {
            return URLDecoder.decode(string, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

