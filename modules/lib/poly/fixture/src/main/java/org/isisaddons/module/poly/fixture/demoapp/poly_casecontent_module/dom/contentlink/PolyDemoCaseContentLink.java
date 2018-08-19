package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;

import com.google.common.base.Function;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "libPolyFixture"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCase", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink "
                        + "WHERE case == :case"),
        @javax.jdo.annotations.Query(
                name = "findByContent", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink "
                        + "WHERE contentObjectType == :contentObjectType "
                        + "   && contentIdentifier == :contentIdentifier ")
})
@javax.jdo.annotations.Unique(name="CaseContentLink_case_content_UNQ", members = {"case","contentObjectType","contentIdentifier"})
@DomainObject
public abstract class PolyDemoCaseContentLink
        extends PolymorphicAssociationLink<PolyDemoCase, PolyDemoCaseContent, PolyDemoCaseContentLink> {

    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<PolyDemoCase, PolyDemoCaseContent, PolyDemoCaseContentLink> {

        public InstantiateEvent(final Object source, final PolyDemoCase aCase, final PolyDemoCaseContent content) {
            super(PolyDemoCaseContentLink.class, source, aCase, content);
        }
    }

    //region > constructor
    public PolyDemoCaseContentLink() {
        super("{subject} contains {polymorphicReference}");
    }
    //endregion

    //region > SubjectPolymorphicReferenceLink API
    @Override
    @Programmatic
    public PolyDemoCase getSubject() {
        return getCase();
    }

    @Override
    @Programmatic
    public void setSubject(final PolyDemoCase subject) {
        setCase(subject);
    }

    @Override
    @Programmatic
    public String getPolymorphicObjectType() {
        return getContentObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicObjectType(final String polymorphicObjectType) {
        setContentObjectType(polymorphicObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicIdentifier() {
        return getContentIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
        setContentIdentifier(polymorphicIdentifier);
    }
    //endregion

    //region > case (property)
    @NotPersistent // because we (have to) have a non-standard name for this field
    private PolyDemoCase _case;
    @Column(
            allowsNull = "false",
            name = "case_id"
    )
    public PolyDemoCase getCase() {
        return _case;
    }

    public void setCase(final PolyDemoCase aCase) {
        this._case = aCase;
    }
    //endregion

    //region > contentObjectType (property)
    private String contentObjectType;

    @Column(allowsNull = "false", length = 255)
    public String getContentObjectType() {
        return contentObjectType;
    }

    public void setContentObjectType(final String contentObjectType) {
        this.contentObjectType = contentObjectType;
    }
    //endregion

    //region > contentIdentifier (property)
    private String contentIdentifier;

    @Column(allowsNull = "false", length = 255)
    public String getContentIdentifier() {
        return contentIdentifier;
    }

    public void setContentIdentifier(final String contentIdentifier) {
        this.contentIdentifier = contentIdentifier;
    }
    //endregion

    public static class Functions {
        public static Function<PolyDemoCaseContentLink, PolyDemoCase> GET_CASE = new Function<PolyDemoCaseContentLink, PolyDemoCase>() {
            @Override
            public PolyDemoCase apply(final PolyDemoCaseContentLink input) {
                return input.getSubject();
            }
        };
        public static Function<PolyDemoCaseContentLink, PolyDemoCaseContent> GET_CONTENT = new Function<PolyDemoCaseContentLink, PolyDemoCaseContent>() {
            @Override
            public PolyDemoCaseContent apply(final PolyDemoCaseContentLink input) {
                return input.getPolymorphicReference();
            }
        };
    }
}
