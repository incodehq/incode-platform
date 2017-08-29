package org.incode.domainapp.example.dom.dom.note.dom.demolink;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class NotableLinkForDemoObjectSubtypeProvider extends NotableLinkRepository.SubtypeProviderAbstract {
    public NotableLinkForDemoObjectSubtypeProvider() {
        super(DemoObject.class, NotableLinkForDemoObject.class);
    }
}
