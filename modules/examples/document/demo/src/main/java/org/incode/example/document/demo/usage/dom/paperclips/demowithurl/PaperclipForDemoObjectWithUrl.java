package org.incode.example.document.demo.usage.dom.paperclips.demowithurl;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.example.document.dom.mixins.T_createAndAttachDocumentAndRender;
import org.incode.example.document.dom.mixins.T_createAndAttachDocumentAndScheduleRender;
import org.incode.example.document.dom.mixins.T_documents;
import org.incode.example.document.dom.mixins.T_preview;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="documentDemoUsage"
)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class PaperclipForDemoObjectWithUrl extends Paperclip {

    //region > deniObject (property)
    private DocDemoObjectWithUrl demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public DocDemoObjectWithUrl getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final DocDemoObjectWithUrl demoObject) {
        this.demoObject = demoObject;
    }
    //endregion


    //region > attachedTo (hook, derived)
    @NotPersistent
    @Override
    public Object getAttachedTo() {
        return getDemoObject();
    }

    @Override
    protected void setAttachedTo(final Object object) {
        setDemoObject((DocDemoObjectWithUrl) object);
    }
    //endregion


    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends PaperclipRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DocDemoObjectWithUrl.class, PaperclipForDemoObjectWithUrl.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _preview extends T_preview<DocDemoObjectWithUrl> {
        public _preview(final DocDemoObjectWithUrl demoObject) {
            super(demoObject);
        }
    }

    @Mixin
    public static class _documents extends T_documents<DocDemoObjectWithUrl> {
        public _documents(final DocDemoObjectWithUrl demoObject) {
            super(demoObject);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndRender extends
            T_createAndAttachDocumentAndRender<DocDemoObjectWithUrl> {
        public _createAndAttachDocumentAndRender(final DocDemoObjectWithUrl demoObject) {
            super(demoObject);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndScheduleRender extends
            T_createAndAttachDocumentAndScheduleRender<DocDemoObjectWithUrl> {
        public _createAndAttachDocumentAndScheduleRender(final DocDemoObjectWithUrl demoObject) {
            super(demoObject);
        }
    }

    //endregion

}
