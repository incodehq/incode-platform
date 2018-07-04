package org.isisaddons.module.freemarker.dom.service;

public class TemplateSource {

    private final String templateName;
    private final String templateChars;

    public TemplateSource(final String templateName, final String templateChars) {
        this.templateName = templateName;
        this.templateChars = templateChars;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getChars() {
        return templateChars;
    }


}
