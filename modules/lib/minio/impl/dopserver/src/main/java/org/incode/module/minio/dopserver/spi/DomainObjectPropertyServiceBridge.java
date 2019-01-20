package org.incode.module.minio.dopserver.spi;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "" + Integer.MAX_VALUE)
public class DomainObjectPropertyServiceBridge {

    public List<DomainObjectProperty> findToArchive() {

        final List<DomainObjectProperty> domainObjectProperties = Lists.newArrayList();

        for (final DomainObjectPropertyProvider propertyProvider : propertyProviders) {
            propertyProvider.findToArchive(domainObjectProperties);
        }

        return domainObjectProperties;
    }

    public Optional<DomainObjectProperty> inspect(
            final String sourceBookmark,
            final String sourceProperty) {

        final Bookmark bookmark = new Bookmark(sourceBookmark);
        final Object domainObject = bookmarkService.lookup(bookmark, BookmarkService2.FieldResetPolicy.DONT_RESET);

        for (final DomainObjectPropertyProvider propertyProvider : propertyProviders) {
            final DomainObjectProperty blobDop =
                    new DomainObjectProperty(sourceBookmark, sourceProperty, DomainObjectProperty.Type.BLOB);
            if(propertyProvider.supportsBlobFor(domainObject, blobDop)) {
                return Optional.of(blobDop);
            }
            final DomainObjectProperty clobDop =
                    new DomainObjectProperty(sourceBookmark, sourceProperty, DomainObjectProperty.Type.CLOB);
            if(propertyProvider.supportsBlobFor(domainObject, blobDop)) {
                return Optional.of(clobDop);
            }
        }

        return Optional.empty();
    }

    /**
     * Will only be called if the provided {@link DomainObjectProperty} has a
     * {@link DomainObjectProperty#getType() type} of {@link DomainObjectProperty.Type#BLOB blob}.
     */
    public Blob blobFor(final DomainObjectProperty dop) {

        final Object domainObject = lookupDomainObject(dop);

        for (final DomainObjectPropertyProvider propertyProvider : propertyProviders) {
            if(propertyProvider.supportsBlobFor(domainObject, dop)) {
                return propertyProvider.blobFor(domainObject, dop);
            }
        }

        return null;
    }

    /**
     * Will only be called if the provided {@link DomainObjectProperty} has a
     * {@link DomainObjectProperty#getType() type} of {@link DomainObjectProperty.Type#CLOB clob}.
     */
    public Clob clobFor(final DomainObjectProperty dop) {
        final Object domainObject = lookupDomainObject(dop);

        for (final DomainObjectPropertyProvider propertyProvider : propertyProviders) {
            if(propertyProvider.supportsClobFor(domainObject, dop)) {
                return propertyProvider.clobFor(domainObject, dop);
            }
        }

        return null;
    }

    public void archived(final DomainObjectProperty dop, final String externalUrl) {

        final Object domainObject = lookupDomainObject(dop);

        for (final DomainObjectPropertyProvider propertyProvider : propertyProviders) {
            switch (dop.getType()) {
            case BLOB:
                if(propertyProvider.supportsBlobFor(domainObject, dop)) {
                    propertyProvider.blobArchived(domainObject, dop, externalUrl);
                }
                break;
            case CLOB:
                if(propertyProvider.supportsClobFor(domainObject, dop)) {
                    propertyProvider.clobArchived(domainObject, dop, externalUrl);
                }
                break;
            }
        }

    }

    private Object lookupDomainObject(final DomainObjectProperty dop) {
        final Bookmark bookmark = new Bookmark(dop.getBookmark());
        return bookmarkService.lookup(bookmark, BookmarkService2.FieldResetPolicy.DONT_RESET);
    }


    @Inject
    BookmarkService2 bookmarkService;


    @Inject
    List<DomainObjectPropertyProvider> propertyProviders;

}
