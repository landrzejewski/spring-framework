package pl.training.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private static final String TEMPLATE_PREFIX = "mails/";
    private static final String LANGUAGE_SEPARATOR = "_";

    private final TemplateEngine templateEngine;

    public String process(String templateBaseName, Map<String, Object> data, String language) {
        var templateName = getTemplateName(templateBaseName, language);
        var context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateName, context);
    }

    private String getTemplateName(String baseName, String language) {
        return TEMPLATE_PREFIX + baseName + LANGUAGE_SEPARATOR + language;
    }

}
