package org.incode.domainapp.example.dom.spi.publishmq.dom.demo;

import javax.annotation.Nullable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Ordering;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.wrapper.WrapperFactory;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import org.incode.domainapp.example.dom.spi.publishmq.dom.touch.Touchable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT )
@DomainObject(publishing = Publishing.ENABLED )             // <<<<<<<<<<<<<<< ENABLED
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class PublishMqDemoObject implements Comparable<PublishMqDemoObject>, Touchable {

    @Builder
    public PublishMqDemoObject(final String name, final String description, final Integer count) {
        this.name = name;
        this.description = description;
        this.count = count;
    }

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @Getter @Setter
    @Property(publishing = Publishing.ENABLED)              // <<<<<<<<<<<<<<< ENABLED
    @PropertyLayout(describedAs = "Publishing enabled")
    @MemberOrder(sequence="1")
    private String name;


    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    @Property(publishing = Publishing.AS_CONFIGURED)        // <<<<<<<<<<<<<<< AS_CONFIGURED
    @PropertyLayout(describedAs = "Publishing as configured")
    private String description;



    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    @Property(publishing = Publishing.DISABLED)             // <<<<<<<<<<<<<<< DISABLED
    @PropertyLayout(describedAs = "Publishing disabled")
    private Integer count;

    //region > updateName (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.ENABLED                 // <<<<<<<<<<<<<<< ENABLED
    )
    @ActionLayout(describedAs = "Publishing enabled")
    public PublishMqDemoObject updateName(
            final String name) {
        setName(name);
        return this;
    }
    public String default0UpdateName() {
        return getName();
    }

    //endregion


    //region > updateDescription (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.AS_CONFIGURED           // <<<<<<<<<<<<<<< AS_CONFIGURED
    )
    @ActionLayout(
            describedAs = "Publishing as configured"
    )
    public PublishMqDemoObject updateDescription(
            @Nullable
            final String description) {
        setDescription(description);
        return this;
    }
    public String default0UpdateDescription() {
        return getDescription();
    }
    //endregion


    //region > updateCount (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            publishing = Publishing.DISABLED            // <<<<<<<<<<<<<<< DISABLED
    )
    @ActionLayout(
            describedAs = "Publishing disabled"
    )
    public PublishMqDemoObject updateCount(
            @Nullable
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
            final String name,
            @Nullable
            final String description,
            @Nullable
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
        return Ordering.natural().onResultOf(PublishMqDemoObject::getName).compare(this, other);
    }

    //endregion


    @javax.inject.Inject
    WrapperFactory wrapperFactory;


}
