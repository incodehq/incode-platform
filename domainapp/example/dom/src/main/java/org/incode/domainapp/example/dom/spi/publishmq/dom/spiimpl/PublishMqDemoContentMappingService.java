package org.incode.domainapp.example.dom.spi.publishmq.dom.spiimpl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.conmap.ContentMappingService;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.schema.common.v1.OidDto;

import org.incode.domainapp.example.publishmq.canonical.DemoObjectDto;
import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObject;


@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishMqDemoContentMappingService implements ContentMappingService {

    @Programmatic
    @Override
    public Object map(
            final Object object,
            final List<MediaType> acceptableMediaTypes) {

        if(object instanceof PublishMqDemoObject) {
            final PublishMqDemoObject demoObject = (PublishMqDemoObject) object;

            final Bookmark bookmark = bookmarkService.bookmarkFor(object);

            final DemoObjectDto dto = new DemoObjectDto();
            dto.setName(demoObject.getName());
            dto.setDescription(demoObject.getDescription());

            final OidDto oidDto = bookmark.toOidDto();

            dto.setOid(oidDto);

            return dto;
        }

        return null;
    }

    @javax.inject.Inject
    private BookmarkService bookmarkService;

}
