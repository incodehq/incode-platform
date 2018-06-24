package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.examples.note.demo.shared.demo.dom.DemoObject;
import org.incode.example.note.dom.impl.notablelink.NotableLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class NotableLinkForDemoObjectSubtypeProvider extends NotableLinkRepository.SubtypeProviderAbstract {
    public NotableLinkForDemoObjectSubtypeProvider() {
        super(DemoObject.class, NotableLinkForDemoObject.class);
    }
}
