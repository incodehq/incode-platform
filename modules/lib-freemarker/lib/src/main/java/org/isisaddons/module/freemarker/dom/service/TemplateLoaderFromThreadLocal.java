package org.isisaddons.module.freemarker.dom.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.template.TemplateException;

class TemplateLoaderFromThreadLocal implements freemarker.cache.TemplateLoader {

    private static final ThreadLocal<TemplateSource> templateSource = new ThreadLocal<>();

    static interface Block {
        String exec(final TemplateSource templateSource) throws IOException, TemplateException;
    }

    static String withTemplateSource(
            final String templateName,
            final String templateChars,
            final Block block)
            throws IOException, TemplateException {
        return withTemplateSource(new TemplateSource(templateName, templateChars), block);
    }

    static String withTemplateSource(final TemplateSource templateSource, final Block block)
            throws IOException, TemplateException {
        TemplateLoaderFromThreadLocal.templateSource.set(templateSource);
        try {
            return block.exec(templateSource);
        } finally {
            TemplateLoaderFromThreadLocal.templateSource.set(null);
        }
    }

    @Override
    public Object findTemplateSource(final String templateName) throws IOException {
        final TemplateSource templateSource = TemplateLoaderFromThreadLocal.templateSource.get();
        if(templateSource == null) {
            throw new IllegalStateException("Not called within withTemplateSource(...) block");
        }
        return templateSource;
    }

    @Override
    public Reader getReader(final Object templateSourceAsObj, final String encoding) throws IOException {
        final TemplateSource templateSource = (TemplateSource) templateSourceAsObj;
        return new StringReader(templateSource.getChars());
    }

    @Override
    public void closeTemplateSource(final Object o) throws IOException {
        // no-op
    }

    @Override
    public long getLastModified(final Object templateSourceAsObj) {
        // versioning is responsibiility of caller; just encode within the template name
        return 1L;
    }

}
