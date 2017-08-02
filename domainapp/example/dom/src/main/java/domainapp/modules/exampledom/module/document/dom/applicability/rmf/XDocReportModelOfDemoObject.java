package domainapp.modules.exampledom.module.document.dom.applicability.rmf;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.isisaddons.module.xdocreport.dom.service.XDocReportModel;

import org.incode.module.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import domainapp.modules.exampledom.module.document.dom.demo.DemoObject;

import lombok.Getter;

public class XDocReportModelOfDemoObject extends RendererModelFactoryAbstract<DemoObject> {

    public XDocReportModelOfDemoObject() {
        super(DemoObject.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DemoObject demoObject) {
        return new DataModel(demoObject);
    }

    public static class DataModel implements XDocReportModel {

        // for freemarker
        @Getter
        private final DemoObject demoObject;

        public DataModel(final DemoObject demoObject) {
            this.demoObject = demoObject;
        }

        // for XDocReport
        @Override
        public Map<String, Data> getContextData() {
            return ImmutableMap.of("demoObject", Data.object(demoObject));
        }

    }
}
