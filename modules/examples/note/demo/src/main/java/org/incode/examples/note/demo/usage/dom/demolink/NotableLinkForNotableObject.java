package org.incode.examples.note.demo.usage.dom.demolink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.example.note.dom.impl.notablelink.NotableLink;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="noteDemoUsage"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject()
public class NotableLinkForNotableObject extends NotableLink {


    @Column(allowsNull = "false", name = "demoObjectId")
    @Getter @Setter
    private NotableObject notableObject;

    public NotableObject getNotableObject() {
        return notableObject;
    }



    @Override
    public Object getNotable() {
        return getNotableObject();
    }

    @Override
    protected void setNotable(final Object object) {
        this.setNotableObject((NotableObject) object);
    }

}
