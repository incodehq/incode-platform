package org.isisaddons.module.security.app.feature;

import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModelLayout;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureType;

@SuppressWarnings("UnusedDeclaration")
@DomainObject(
        objectType = "isissecurity.ApplicationPackage"
)
@ViewModelLayout(paged=100)
public class ApplicationPackage extends ApplicationFeatureViewModel {

    public static abstract class PropertyDomainEvent<T> extends ApplicationFeatureViewModel.PropertyDomainEvent<ApplicationClass, T> {}

    public static abstract class CollectionDomainEvent<T> extends ApplicationFeatureViewModel.CollectionDomainEvent<ApplicationClass, T> {}

    public static abstract class ActionDomainEvent extends ApplicationFeatureViewModel.ActionDomainEvent<ApplicationClass> {}

    // //////////////////////////////////////

    //region > constructors

    public ApplicationPackage() {
    }

    public ApplicationPackage(final ApplicationFeatureId featureId) {
        super(featureId);
    }
    //endregion

    //region > contents (collection, for packages only)

    public static class ContentsDomainEvent extends CollectionDomainEvent<ApplicationPackage> {}

    @Collection(
            domainEvent = ContentsDomainEvent.class
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "4")
    public List<ApplicationFeatureViewModel> getContents() {
        final SortedSet<ApplicationFeatureId> contents = getFeature().getContents();
        return asViewModels(contents);
    }
    public boolean hideContents() {
        return getType() != ApplicationFeatureType.PACKAGE;
    }
    //endregion

}
