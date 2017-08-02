package domainapp.modules.exampledom.module.document.dom.applicability.rmf;

import org.isisaddons.module.stringinterpolator.dom.StringInterpolatorService;

import org.incode.module.document.dom.impl.applicability.RendererModelFactoryAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import domainapp.modules.exampledom.module.document.dom.demo.DemoObject;

import lombok.Getter;

public class StringInterpolatorRootOfDemoObject extends RendererModelFactoryAbstract<DemoObject> {

    public StringInterpolatorRootOfDemoObject() {
        super(DemoObject.class);
    }

    @Override
    protected Object doNewRendererModel(
            final DocumentTemplate documentTemplate, final DemoObject demoObject) {
        return new DataModel(demoObject);
    }

    public static class DataModel extends StringInterpolatorService.Root {
        @Getter
        private final DemoObject demoObject;

        public DataModel(final DemoObject demoObject) {
            super(demoObject);
            this.demoObject = demoObject;
        }
    }
}
