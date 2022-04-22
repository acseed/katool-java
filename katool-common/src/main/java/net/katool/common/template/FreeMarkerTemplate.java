package net.katool.common.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * @author hongchen.cao
 * @since 21 四月 2022
 */
@Slf4j
public class FreeMarkerTemplate implements MessageTemplate {
    private final FreeMarkerConfigurer configurer;
    public FreeMarkerTemplate(FreeMarkerConfigurer configurer) {
        this.configurer = configurer;
    }
    
    @Override
    public String render(String templateName, Map<String, Object> params) {
        try {
            Configuration configuration = configurer.getConfiguration();
            Template template = configuration.getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
