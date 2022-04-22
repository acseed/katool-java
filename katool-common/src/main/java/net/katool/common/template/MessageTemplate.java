package net.katool.common.template;

import java.util.Map;

/**
 * @author hongchen.cao
 * @since 21 四月 2022
 */
public interface MessageTemplate {
    /**
     * 按照模板渲染
     * @param params
     * @param templateName
     * @return
     */
    String render(String templateName, Map<String, Object> params);
}
