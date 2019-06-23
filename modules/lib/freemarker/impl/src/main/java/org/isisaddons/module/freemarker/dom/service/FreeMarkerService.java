package org.isisaddons.module.freemarker.dom.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.base.AbstractInstant;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.core.plugins.environment.IsisSystemEnvironment;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.Template;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@DomainService(nature = NatureOfService.DOMAIN)
public class FreeMarkerService {

    public static final String JODA_SUPPORT_KEY = "isis.services.addons.freemarker.jodaSupport";
    private static final String JODA_SUPPORT_DEFAULT = "true";

    private TemplateLoaderFromThreadLocal templateLoader;
    private Configuration cfg;

    @PostConstruct
    public void init(Map<String,String> properties) {
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setDefaultEncoding("UTF-8");

        String jodaSupportStr = properties.get(JODA_SUPPORT_KEY);;
        if(jodaSupportStr == null) {
            jodaSupportStr = JODA_SUPPORT_DEFAULT;
        }
        boolean jodaSupport = Boolean.parseBoolean(jodaSupportStr);
        if(jodaSupport) {
            cfg.setObjectWrapper(new JodaObjectWrapper());
        }


        final boolean isPrototyping = isPrototyping();
        final TemplateExceptionHandler handler = isPrototyping
                        ? TemplateExceptionHandler.HTML_DEBUG_HANDLER
                        : TemplateExceptionHandler.RETHROW_HANDLER;
        cfg.setTemplateExceptionHandler(handler);

        templateLoader = new TemplateLoaderFromThreadLocal();
        cfg.setTemplateLoader(templateLoader);

        cfg.setLogTemplateExceptions(true);
    }

    /**
     * Uses the provided document type (reference) and atPath to lookup the template's text.
     *
     * @param dataModel - will usually be a strongly-typed DTO, but a {@link Map} can also be used.
     */
    @Programmatic
    public String render(
            final String templateName,
            final String templateChars,
            final Object dataModel) throws IOException, TemplateException {
        return templateLoader
                .withTemplateSource(templateName, templateChars, templateSource -> {
                    final StringWriter sw = new StringWriter();
                    final Template template = cfg.getTemplate(templateSource.getTemplateName());
                    template.process(dataModel, sw);
                    return sw.toString();
                });
    }



    private static class JodaObjectWrapper extends DefaultObjectWrapper {
        public JodaObjectWrapper() {
            super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        }

        @Override
        public TemplateModel wrap(final Object obj) throws TemplateModelException {
            // handles DateTime
            if (obj instanceof AbstractInstant) {
                return new SimpleDate(((AbstractInstant) obj).toDate(), TemplateDateModel.DATETIME);
            }
            if (obj instanceof LocalDate) {
                return new SimpleDate(((LocalDate) obj).toDate(), TemplateDateModel.DATE);
            }
            if (obj instanceof LocalDateTime) {
                return new SimpleDate(((LocalDateTime) obj).toDate(), TemplateDateModel.DATETIME);
            }
            if (obj instanceof LocalTime) {
                return new SimpleDate(((LocalTime) obj).toDateTimeToday().toDate(), TemplateDateModel.TIME);
            }

            return super.wrap(obj);
        }
    }

    protected boolean isPrototyping() {
        return IsisSystemEnvironment.getDefault().getDeploymentType().isPrototyping();
    }

}



