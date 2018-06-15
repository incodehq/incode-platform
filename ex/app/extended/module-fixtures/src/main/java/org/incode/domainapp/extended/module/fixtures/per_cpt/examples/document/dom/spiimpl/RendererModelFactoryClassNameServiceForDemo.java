package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.spiimpl;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.example.document.dom.impl.applicability.RendererModelFactory;
import org.incode.example.document.dom.services.ClassNameServiceAbstract;
import org.incode.example.document.dom.services.ClassNameViewModel;
import org.incode.example.document.dom.spi.RendererModelFactoryClassNameService;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class RendererModelFactoryClassNameServiceForDemo extends ClassNameServiceAbstract<RendererModelFactory> implements
        RendererModelFactoryClassNameService {

    public RendererModelFactoryClassNameServiceForDemo() {
        super(RendererModelFactory.class, "org.incode.example.document.fixture");
    }

    @Programmatic
    @Override public List<ClassNameViewModel> rendererModelFactoryClassNames() {
        return this.classNames();
    }
}
