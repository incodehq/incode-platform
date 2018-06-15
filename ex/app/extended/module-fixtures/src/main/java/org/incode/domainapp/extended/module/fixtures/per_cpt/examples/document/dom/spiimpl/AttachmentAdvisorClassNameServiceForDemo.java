package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.spiimpl;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.example.document.dom.impl.applicability.AttachmentAdvisor;
import org.incode.example.document.dom.services.ClassNameServiceAbstract;
import org.incode.example.document.dom.services.ClassNameViewModel;
import org.incode.example.document.dom.spi.AttachmentAdvisorClassNameService;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class AttachmentAdvisorClassNameServiceForDemo extends ClassNameServiceAbstract<AttachmentAdvisor> implements
        AttachmentAdvisorClassNameService {

    public AttachmentAdvisorClassNameServiceForDemo() {
        super(AttachmentAdvisor.class, "org.incode.example.document.fixture");
    }

    @Programmatic
    @Override public List<ClassNameViewModel> attachmentAdvisorClassNames() {
        return this.classNames();
    }
}
