package org.incode.example.document.demo.usage.dom.applicability.rmf;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.isisaddons.module.xdocreport.dom.service.XDocReportModel;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;

import lombok.Getter;

public class XDocReportModelOfDemoObject extends RendererModelFactoryAbstract<DocDemoObjectWithUrl> {

    public XDocReportModelOfDemoObject() {
        super(DocDemoObjectWithUrl.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DocDemoObjectWithUrl demoObject) {
        return new DataModel(demoObject);
    }

    public static class DataModel implements XDocReportModel {

        // for freemarker
        @Getter
        private final DocDemoObjectWithUrl demoObject;

        public DataModel(final DocDemoObjectWithUrl demoObject) {
            this.demoObject = demoObject;
        }

        // for XDocReport
        @Override
        public Map<String, Data> getContextData() {
            return ImmutableMap.of("demoObject", Data.object(demoObject));
        }

    }
}
