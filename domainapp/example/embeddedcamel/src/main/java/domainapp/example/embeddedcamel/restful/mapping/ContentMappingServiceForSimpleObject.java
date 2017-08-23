package domainapp.example.embeddedcamel.restful.mapping;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.conmap.ContentMappingService;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.schema.common.v1.OidDto;

import org.incode.domainapp.example.canonical.SimpleObjectDto;

import domainapp.modules.simple.dom.SimpleObject;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ContentMappingServiceForSimpleObject implements ContentMappingService {

    @Programmatic
    @Override
    public Object map(
            final Object object,
            final List<MediaType> acceptableMediaTypes) {

        if(object instanceof SimpleObject) {
            final SimpleObject simpleObject = (SimpleObject) object;

            final Bookmark bookmark = bookmarkService.bookmarkFor(object);

            final SimpleObjectDto dto = new SimpleObjectDto();
            dto.setName(simpleObject.getName());
            dto.setNotes(simpleObject.getNotes());

            final OidDto oidDto = bookmark.toOidDto();

            dto.setOid(oidDto);

            return dto;
        }

        return null;
    }

    @javax.inject.Inject
    BookmarkService bookmarkService;

}
