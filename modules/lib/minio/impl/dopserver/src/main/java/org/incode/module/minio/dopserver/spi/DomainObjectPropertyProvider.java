package org.incode.module.minio.dopserver.spi;

import java.util.List;

import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

public interface DomainObjectPropertyProvider {

    void findToArchive(List<DomainObjectProperty> appendTo);

    boolean supportsBlobFor(Object domainObject, DomainObjectProperty dop);
    Blob blobFor(Object domainObject, DomainObjectProperty dop);
    void blobArchived(Object domainObject, DomainObjectProperty dop, String externalUrl);

    boolean supportsClobFor(Object domainObject, DomainObjectProperty dop);
    Clob clobFor(Object domainObject, DomainObjectProperty dop);
    void clobArchived(Object domainObject, DomainObjectProperty dop, String externalUrl);
}
