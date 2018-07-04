package org.incode.example.communications.demo.usage.dom.paperclips;

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

import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsInvoice;
import org.incode.example.document.dom.impl.paperclips.Paperclip;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.example.document.dom.mixins.T_createAndAttachDocumentAndRender;
import org.incode.example.document.dom.mixins.T_createAndAttachDocumentAndScheduleRender;
import org.incode.example.document.dom.mixins.T_documents;
import org.incode.example.document.dom.mixins.T_preview;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="communicationsDemoUsage"
)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        // objectType inferred from schema
)
public class PaperclipForDemoInvoice extends Paperclip {

    //region > notableObject (property)
    private CommsInvoice commsInvoice;

    @Column(
            allowsNull = "false",
            name = "invoiceId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public CommsInvoice getCommsInvoice() {
        return commsInvoice;
    }

    public void setCommsInvoice(final CommsInvoice commsInvoice) {
        this.commsInvoice = commsInvoice;
    }
    //endregion


    //region > attachedTo (hook, derived)
    @NotPersistent
    @Override
    public Object getAttachedTo() {
        return getCommsInvoice();
    }

    @Override
    protected void setAttachedTo(final Object object) {
        setCommsInvoice((CommsInvoice) object);
    }
    //endregion


    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends PaperclipRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(CommsInvoice.class, PaperclipForDemoInvoice.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _preview extends T_preview<CommsInvoice> {
        public _preview(final CommsInvoice commsInvoice) {
            super(commsInvoice);
        }
    }

    @Mixin
    public static class _documents extends T_documents<CommsInvoice> {
        public _documents(final CommsInvoice commsInvoice) {
            super(commsInvoice);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndRender extends T_createAndAttachDocumentAndRender<CommsInvoice> {
        public _createAndAttachDocumentAndRender(final CommsInvoice commsInvoice) {
            super(commsInvoice);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndScheduleRender extends
            T_createAndAttachDocumentAndScheduleRender<CommsInvoice> {
        public _createAndAttachDocumentAndScheduleRender(final CommsInvoice commsInvoice) {
            super(commsInvoice);
        }
    }

    //endregion

}
