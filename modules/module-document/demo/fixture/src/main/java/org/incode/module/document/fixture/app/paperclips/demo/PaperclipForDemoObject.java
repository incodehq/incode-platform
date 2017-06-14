package org.incode.module.document.fixture.app.paperclips.demo;

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

import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.mixins.T_createAndAttachDocumentAndRender;
import org.incode.module.document.dom.mixins.T_createAndAttachDocumentAndScheduleRender;
import org.incode.module.document.dom.mixins.T_documents;
import org.incode.module.document.dom.mixins.T_preview;
import org.incode.module.document.fixture.dom.demo.DemoObject;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="incodeDocumentDemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class PaperclipForDemoObject extends Paperclip {

    //region > demoObject (property)
    private DemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public DemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final DemoObject demoObject) {
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
        setDemoObject((DemoObject) object);
    }
    //endregion


    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends PaperclipRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DemoObject.class, PaperclipForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _preview extends T_preview<DemoObject> {
        public _preview(final DemoObject demoObject) {
            super(demoObject);
        }
    }

    @Mixin
    public static class _documents extends T_documents<DemoObject> {
        public _documents(final DemoObject demoObject) {
            super(demoObject);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndRender extends T_createAndAttachDocumentAndRender<DemoObject> {
        public _createAndAttachDocumentAndRender(final DemoObject demoObject) {
            super(demoObject);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndScheduleRender extends T_createAndAttachDocumentAndScheduleRender<DemoObject> {
        public _createAndAttachDocumentAndScheduleRender(final DemoObject demoObject) {
            super(demoObject);
        }
    }

    //endregion

}
