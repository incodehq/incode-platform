package org.isisaddons.module.security.app.user;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeature;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureRepositoryDefault;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.user.ApplicationUser;

@Mixin
public class ApplicationUser_permissions {


    public static class ActionDomainEvent extends SecurityModule.ActionDomainEvent<ApplicationUser_permissions> {}

    //region > constructor
    private final ApplicationUser user;
    public ApplicationUser_permissions(final ApplicationUser user) {
        this.user = user;
    }
    //endregion


    @Action(
        semantics = SemanticsOf.SAFE,
        domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            paged=50,
            defaultView = "table"
    )
    @MemberOrder(sequence = "30")
    public List<UserPermissionViewModel> $$() {
        final java.util.Collection<ApplicationFeature> allMembers = applicationFeatureRepository.allMembers();
        return asViewModels(allMembers);
    }

    List<UserPermissionViewModel> asViewModels(final Iterable<ApplicationFeature> features) {
        return Lists.newArrayList(
                Iterables.transform(
                        features,
                        UserPermissionViewModel.Functions.asViewModel(user, container))
        );
    }

    @javax.inject.Inject
    DomainObjectContainer container;
    @javax.inject.Inject
    ApplicationFeatureRepositoryDefault applicationFeatureRepository;

}
