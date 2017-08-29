package org.incode.domainapp.example.dom.dom.note.dom.demolink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.note.dom.impl.notablelink.NotableLink;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="exampleDomNote"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject()
public class NotableLinkForDemoObject extends NotableLink {


    @Column(allowsNull = "false", name = "demoObjectId")
    @Getter @Setter
    private DemoObject demoObject;

    public DemoObject getDemoObject() {
        return demoObject;
    }



    @Override
    public Object getNotable() {
        return getDemoObject();
    }

    @Override
    protected void setNotable(final Object object) {
        setDemoObject((DemoObject) object);
    }

}
