package org.isisaddons.module.security.app.feature;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.ViewModelLayout;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;

@DomainObject(
        objectType = "isissecurity.ApplicationClassMember"
)
@ViewModelLayout(
        bookmarking = BookmarkPolicy.AS_CHILD
)
public abstract class ApplicationClassMember extends ApplicationFeatureViewModel {

    public static abstract class PropertyDomainEvent<S extends ApplicationClassMember, T> extends ApplicationFeatureViewModel.PropertyDomainEvent<ApplicationClassMember, T> {}

    public static abstract class CollectionDomainEvent<S extends ApplicationClassMember, T> extends ApplicationFeatureViewModel.CollectionDomainEvent<S, T> {}

    public static abstract class ActionDomainEvent<S extends ApplicationClassMember> extends ApplicationFeatureViewModel.ActionDomainEvent<S> {}

    // //////////////////////////////////////

    //region > constructors
    public ApplicationClassMember() {
    }

    public ApplicationClassMember(final ApplicationFeatureId featureId) {
        super(featureId);
    }
    //endregion

    //region > memberName (properties)

    public static class MemberNameDomainEvent extends PropertyDomainEvent<ApplicationClassMember, String> {}

    @Property(
            domainEvent = MemberNameDomainEvent.class
    )
    @MemberOrder(name="Id", sequence = "2.4")
    public String getMemberName() {
        return super.getMemberName();
    }
    //endregion



}


