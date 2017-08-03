package org.incode.domainapp.example.dom.spi.publishmq.dom.demo;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.wrapper.WrapperFactory;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.domainapp.example.dom.spi.publishmq.dom.touch.Touchable;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema="exampleSpiPublishMq"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        publishing = Publishing.ENABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class PublishMqDemoObject implements Comparable<PublishMqDemoObject>, Touchable {

    //region > name (property)
    
    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @Property(
            publishing = Publishing.ENABLED
    )
    @PropertyLayout(
            describedAs = "Publishing enabled"
    )
    @MemberOrder(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion

    //region > updateName (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.ENABLED
    )
    @ActionLayout(
            describedAs = "Publishing enabled"
    )
    public PublishMqDemoObject updateName(
            @ParameterLayout(named="Name")
            final String name) {
        setName(name);
        return this;
    }
    public String default0UpdateName() {
        return getName();
    }
    //endregion

    //region > description (property)

    private String description;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            publishing = Publishing.AS_CONFIGURED
    )
    @PropertyLayout(
            describedAs = "Publishing as configured"
    )
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    //endregion

    //region > updateDescription (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.AS_CONFIGURED
    )
    @ActionLayout(
            describedAs = "Publishing as configured"
    )
    public PublishMqDemoObject updateDescription(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description) {
        setDescription(description);
        return this;
    }
    public String default0UpdateDescription() {
        return getDescription();
    }
    //endregion

    //region > count (property)

    private Integer count;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            publishing = Publishing.DISABLED
    )
    @PropertyLayout(
            describedAs = "Publishing disabled"
    )
    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    //endregion

    //region > updateCount (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.DISABLED
    )
    @ActionLayout(
            describedAs = "Publishing disabled"
    )
    public PublishMqDemoObject updateCount(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named="Count")
            final Integer count) {
        setCount(count);
        return this;
    }
    public Integer default0UpdateCount() {
        return getCount();
    }
    //endregion

    //region > updateCountInBulk (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.ENABLED,
            invokeOn = InvokeOn.COLLECTION_ONLY
    )
    @ActionLayout(
            describedAs = "Publishing enabled, bulk action"
    )
    public void incrementCountInBulk() {
        setCount( currentCount() + 1 );
    }

    private int currentCount() {
        return getCount() != null ? getCount() : 0;
    }
    //endregion

    //region > updateNameAndDescriptionAndCount (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.ENABLED
    )
    @ActionLayout(
            describedAs = "Updates name, description and count as sub-actions"
    )
    public PublishMqDemoObject updateNameAndDescriptionAndCount(
            @ParameterLayout(named="Name")
            final String name,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named="Description")
            final String description,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named="Count")
            final Integer count) {
        wrapperFactory.wrap(this).updateName(name);
        wrapperFactory.wrap(this).updateDescription(description);
        wrapperFactory.wrap(this).updateCount(count);
        return this;
    }
    public String default0UpdateNameAndDescriptionAndCount() {
        return getName();
    }
    public String default1UpdateNameAndDescriptionAndCount() {
        return getDescription();
    }
    public Integer default2UpdateNameAndDescriptionAndCount() {
        return getCount();
    }
    //endregion



    //region > compareTo

    @Override
    public int compareTo(PublishMqDemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private WrapperFactory wrapperFactory;

    //endregion

}
