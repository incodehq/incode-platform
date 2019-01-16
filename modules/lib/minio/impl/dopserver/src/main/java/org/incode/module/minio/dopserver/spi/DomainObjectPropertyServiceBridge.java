package org.incode.module.minio.dopserver.spi;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

public interface DomainObjectPropertyServiceBridge {

    @Programmatic
    List<DomainObjectProperty> findToArchive(String caller);

    /**
     * Will only be called if the provided {@link DomainObjectProperty} has a
     * {@link DomainObjectProperty#getType() type} of {@link DomainObjectProperty.Type#BLOB blob}.
     */
    @Programmatic
    Blob blobFor(DomainObjectProperty domainObjectProperty);

    /**
     * Will only be called if the provided {@link DomainObjectProperty} has a
     * {@link DomainObjectProperty#getType() type} of {@link DomainObjectProperty.Type#CLOB clob}.
     */
    @Programmatic
    Clob clobFor(DomainObjectProperty domainObjectProperty);

    @Programmatic
    void archive(DomainObjectProperty domainObjectProperty, String externalUrl);

    @DomainService(nature = NatureOfService.DOMAIN, menuOrder = "" + Integer.MAX_VALUE)
    class Noop implements DomainObjectPropertyServiceBridge {

        public List<DomainObjectProperty> findToArchive(String caller) {
            return Collections.emptyList();
        }

        @Override
        public Blob blobFor(final DomainObjectProperty domainObjectProperty) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Clob clobFor(final DomainObjectProperty domainObjectProperty) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void archive(final DomainObjectProperty domainObjectProperty, String externalUrl) {
            throw new UnsupportedOperationException();
        }
    }
}
