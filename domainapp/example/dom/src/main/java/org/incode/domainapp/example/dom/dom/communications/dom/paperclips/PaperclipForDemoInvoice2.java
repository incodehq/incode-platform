package org.incode.domainapp.example.dom.dom.communications.dom.paperclips;

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

import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.mixins.T_createAndAttachDocumentAndRender;
import org.incode.module.document.dom.mixins.T_createAndAttachDocumentAndScheduleRender;
import org.incode.module.document.dom.mixins.T_documents;
import org.incode.module.document.dom.mixins.T_preview;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="exampleDomCommunications"
)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        // objectType inferred from schema
)
public class PaperclipForDemoInvoice2 extends Paperclip {

    //region > demoObject (property)
    private DemoInvoice2 demoInvoice;

    @Column(
            allowsNull = "false",
            name = "invoiceId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public DemoInvoice2 getDemoInvoice() {
        return demoInvoice;
    }

    public void setDemoInvoice(final DemoInvoice2 demoInvoice) {
        this.demoInvoice = demoInvoice;
    }
    //endregion


    //region > attachedTo (hook, derived)
    @NotPersistent
    @Override
    public Object getAttachedTo() {
        return getDemoInvoice();
    }

    @Override
    protected void setAttachedTo(final Object object) {
        setDemoInvoice((DemoInvoice2) object);
    }
    //endregion


    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends PaperclipRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DemoInvoice2.class, PaperclipForDemoInvoice2.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _preview extends T_preview<DemoInvoice2> {
        public _preview(final DemoInvoice2 demoInvoice) {
            super(demoInvoice);
        }
    }

    @Mixin
    public static class _documents extends T_documents<DemoInvoice2> {
        public _documents(final DemoInvoice2 demoInvoice) {
            super(demoInvoice);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndRender extends T_createAndAttachDocumentAndRender<DemoInvoice2> {
        public _createAndAttachDocumentAndRender(final DemoInvoice2 demoInvoice) {
            super(demoInvoice);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndScheduleRender extends T_createAndAttachDocumentAndScheduleRender<DemoInvoice2> {
        public _createAndAttachDocumentAndScheduleRender(final DemoInvoice2 demoInvoice) {
            super(demoInvoice);
        }
    }

    //endregion

}
