package org.isisaddons.module.security.app.feature;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureRepositoryDefault;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.permission.ApplicationPermission;

@Mixin
public class ApplicationPermission_feature {

    public static class ActionDomainEvent extends SecurityModule.ActionDomainEvent<ApplicationPermission_feature> {}


    //region > constructor
    private final ApplicationPermission permission;
    public ApplicationPermission_feature(final ApplicationPermission permission) {
        this.permission = permission;
    }
    //endregion

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @Property(
    )
    @PropertyLayout(
            hidden=Where.REFERENCES_PARENT
    )
    @MemberOrder(name="Feature", sequence = "4")
    public ApplicationFeatureViewModel $$(final ApplicationPermission permission) {
        if(permission.getFeatureType() == null) {
            return null;
        }
        final ApplicationFeatureId featureId = getFeatureId(permission);
        return ApplicationFeatureViewModel.newViewModel(featureId, applicationFeatureRepository, container);
    }

    private static ApplicationFeatureId getFeatureId(final ApplicationPermission permission) {
        return ApplicationFeatureId.newFeature(permission.getFeatureType(), permission.getFeatureFqn());
    }

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    ApplicationFeatureRepositoryDefault applicationFeatureRepository;

}
