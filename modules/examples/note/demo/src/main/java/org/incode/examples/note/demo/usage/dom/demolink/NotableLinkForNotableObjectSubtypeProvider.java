package org.incode.examples.note.demo.usage.dom.demolink;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.example.note.dom.impl.notablelink.NotableLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class NotableLinkForNotableObjectSubtypeProvider extends NotableLinkRepository.SubtypeProviderAbstract {
    public NotableLinkForNotableObjectSubtypeProvider() {
        super(NotableObject.class, NotableLinkForNotableObject.class);
    }
}
