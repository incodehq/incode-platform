package org.incode.domainapp.example.dom.dom.note.dom.demolink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.module.note.dom.impl.note.T_addNote;
import org.incode.module.note.dom.impl.note.T_notes;
import org.incode.module.note.dom.impl.note.T_removeNote;
import org.incode.domainapp.example.dom.dom.note.dom.demo.NoteDemoObject;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="exampleDomNote"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject()
public class NotableLinkForDemoObject extends NotableLink {


    //region > demoObject (property)
    private NoteDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    public NoteDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final NoteDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > notable (hook, derived)

    @Override
    public Object getNotable() {
        return getDemoObject();
    }

    @Override
    protected void setNotable(final Object object) {
        setDemoObject((NoteDemoObject) object);
    }

    //endregion

    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends NotableLinkRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(NoteDemoObject.class, NotableLinkForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _notes extends T_notes<NoteDemoObject> {
        public _notes(final NoteDemoObject notable) {
            super(notable);
        }
    }

    @Mixin
    public static class _addNote extends T_addNote<NoteDemoObject> {
        public _addNote(final NoteDemoObject notable) {
            super(notable);
        }
    }

    @Mixin
    public static class _removeNote extends T_removeNote<NoteDemoObject> {
        public _removeNote(final NoteDemoObject notable) {
            super(notable);
        }
    }

    //endregion


}
