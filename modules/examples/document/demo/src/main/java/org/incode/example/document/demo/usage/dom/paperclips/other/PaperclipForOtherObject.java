package org.incode.example.document.demo.usage.dom.paperclips.other;

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

import org.incode.example.document.demo.shared.other.dom.DocOtherObject;
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
public class PaperclipForOtherObject extends Paperclip {

    //region > otherObject (property)
    private DocOtherObject otherObject;

    @Column(
            allowsNull = "false",
            name = "otherObjectId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public DocOtherObject getOtherObject() {
        return otherObject;
    }

    public void setOtherObject(final DocOtherObject otherObject) {
        this.otherObject = otherObject;
    }
    //endregion


    //region > attachedTo (hook, derived)
    @NotPersistent
    @Override
    public Object getAttachedTo() {
        return getOtherObject();
    }

    @Override
    protected void setAttachedTo(final Object object) {
        setOtherObject((DocOtherObject) object);
    }
    //endregion


    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends PaperclipRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DocOtherObject.class, PaperclipForOtherObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _preview extends T_preview<DocOtherObject> {
        public _preview(final DocOtherObject otherObject) {
            super(otherObject);
        }
    }

    @Mixin
    public static class _documents extends T_documents<DocOtherObject> {
        public _documents(final DocOtherObject otherObject) {
            super(otherObject);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndRender extends T_createAndAttachDocumentAndRender<DocOtherObject> {
        public _createAndAttachDocumentAndRender(final DocOtherObject otherObject) {
            super(otherObject);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndScheduleRender extends
            T_createAndAttachDocumentAndScheduleRender<DocOtherObject> {
        public _createAndAttachDocumentAndScheduleRender(final DocOtherObject otherObject) {
            super(otherObject);
        }
    }

    //endregion

}
