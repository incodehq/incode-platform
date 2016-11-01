package org.incode.module.document.fixture.app.spiimpl;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.services.ClassNameServiceAbstract;
import org.incode.module.document.dom.services.ClassNameViewModel;
import org.incode.module.document.dom.spi.BinderClassNameService;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class BinderClassNameServiceForDemo extends ClassNameServiceAbstract<Binder> implements BinderClassNameService {

    public BinderClassNameServiceForDemo() {
        super(Binder.class, "org.incode.module.document.fixture");
    }

    @Programmatic
    @Override
    public List<ClassNameViewModel> binderClassNames() {
        return this.classNames();
    }
}
