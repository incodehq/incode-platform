package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom;

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
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoPolyDemoCasePrimaryContentLink "
                        + "WHERE case == :case"),
        @javax.jdo.annotations.Query(
                name = "findByContent", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoPolyDemoCasePrimaryContentLink "
                        + "WHERE contentObjectType == :contentObjectType "
                        + "   && contentIdentifier == :contentIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findByCaseAndContent", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoPolyDemoCasePrimaryContentLink "
                        + "WHERE case == :case "
                        + "   && contentObjectType == :contentObjectType "
                        + "   && contentIdentifier == :contentIdentifier ")
})
@javax.jdo.annotations.Unique(name="CasePrimaryContentLink_case_content_UNQ", members = {"case","contentObjectType","contentIdentifier"})
@DomainObject
public abstract class PolyDemoPolyDemoCasePrimaryContentLink
        extends PolymorphicAssociationLink<PolyDemoCase, PolyDemoCaseContent, PolyDemoPolyDemoCasePrimaryContentLink> {

    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<PolyDemoCase, PolyDemoCaseContent, PolyDemoPolyDemoCasePrimaryContentLink> {

        public InstantiateEvent(final Object source, final PolyDemoCase aCase, final PolyDemoCaseContent content) {
            super(PolyDemoPolyDemoCasePrimaryContentLink.class, source, aCase, content);
        }
    }

    //region > constructor
    public PolyDemoPolyDemoCasePrimaryContentLink() {
        super("{subject} primary {polymorphicReference}");
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
        public static Function<PolyDemoPolyDemoCasePrimaryContentLink, PolyDemoCase> GET_CASE = new Function<PolyDemoPolyDemoCasePrimaryContentLink, PolyDemoCase>() {
            @Override
            public PolyDemoCase apply(final PolyDemoPolyDemoCasePrimaryContentLink input) {
                return input.getSubject();
            }
        };
        public static Function<PolyDemoPolyDemoCasePrimaryContentLink, PolyDemoCaseContent> GET_CONTENT = new Function<PolyDemoPolyDemoCasePrimaryContentLink, PolyDemoCaseContent>() {
            @Override
            public PolyDemoCaseContent apply(final PolyDemoPolyDemoCasePrimaryContentLink input) {
                return input.getPolymorphicReference();
            }
        };
    }

}
