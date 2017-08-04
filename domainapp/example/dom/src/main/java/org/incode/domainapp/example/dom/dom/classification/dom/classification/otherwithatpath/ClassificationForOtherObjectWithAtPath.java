package org.incode.domainapp.example.dom.dom.classification.dom.classification.otherwithatpath;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;

import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.Classification;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="exampleDomClassification"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class ClassificationForOtherObjectWithAtPath extends Classification {


    @Column(allowsNull = "false", name = "otherObjectId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private OtherObjectWithAtPath otherObject;



    @Override
    public Object getClassified() {
        return getOtherObject();
    }

    @Override
    protected void setClassified(final Object classified) {
        setOtherObject((OtherObjectWithAtPath) classified);
    }


}
