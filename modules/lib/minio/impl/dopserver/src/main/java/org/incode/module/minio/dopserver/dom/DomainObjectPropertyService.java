package org.incode.module.minio.dopserver.dom;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.minio.dopserver.spi.DomainObjectProperty;
import org.incode.module.minio.dopserver.spi.DomainObjectPropertyServiceBridge;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY,
        objectType = "incodeMinio.DomainObjectPropertyService"
)
public class DomainObjectPropertyService {

    @Action(semantics = SemanticsOf.SAFE)
    public List<DomainObjectPropertyValueViewModel> findToArchive(String caller) {
        return domainObjectPropertyServiceBridge.findToArchive()
                .stream()
                .map(DomainObjectPropertyValueViewModel::new)
                .collect(Collectors.toList());
    }

    @Action(semantics = SemanticsOf.SAFE)
    public DomainObjectPropertyValueViewModel readSingle(
            final String caller,
            final String sourceBookmark,
            final String sourceProperty) {
        return domainObjectPropertyServiceBridge
                .inspect(sourceBookmark, sourceProperty)
                .map(DomainObjectPropertyValueViewModel::new)
                .orElse(null);

    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public void archived(
            final String sourceBookmark,
            final String sourceProperty,
            final DomainObjectProperty.Type type,
            final String externalUrl) {
        final DomainObjectProperty dop = new DomainObjectProperty(sourceBookmark, sourceProperty, type);
        domainObjectPropertyServiceBridge.archived(dop, externalUrl);
    }

    @Inject
    DomainObjectPropertyServiceBridge domainObjectPropertyServiceBridge;

}


