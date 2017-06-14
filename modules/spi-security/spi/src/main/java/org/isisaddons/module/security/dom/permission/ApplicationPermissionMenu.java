package org.isisaddons.module.security.dom.permission;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.security.SecurityModule;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isissecurity.ApplicationPermissionMenu"
)
@DomainServiceLayout(
        named="Security",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "100.50"
)
public class ApplicationPermissionMenu {

        //region > domain event classes
        public static abstract class PropertyDomainEvent<T> extends SecurityModule.PropertyDomainEvent<ApplicationPermissions, T> {}

        public static abstract class CollectionDomainEvent<T> extends SecurityModule.CollectionDomainEvent<ApplicationPermissions, T> {}

        public static abstract class ActionDomainEvent extends SecurityModule.ActionDomainEvent<ApplicationPermissions> {}
        //endregion

        //region > iconName
        public String iconName() {
            return "applicationPermission";
        }
        //endregion

        //region > findOrphanedPermissions (action)
        public static class FindOrphanedPermissionsDomainEvent extends ActionDomainEvent {}

        @Action(
                domainEvent=FindOrphanedPermissionsDomainEvent.class,
                semantics = SemanticsOf.SAFE
        )
        @MemberOrder(sequence = "100.50.1")
        public List<ApplicationPermission> findOrphanedPermissions() {
                return applicationPermissionRepository.findOrphaned();
        }
        //endregion

        //region > allPermissions (action)
        public static class AllPermissionsDomainEvent extends ActionDomainEvent {}

        @Action(
                domainEvent=AllPermissionsDomainEvent.class,
                semantics = SemanticsOf.SAFE,
                restrictTo = RestrictTo.PROTOTYPING
        )
        @MemberOrder(sequence = "100.50.2")
        public List<ApplicationPermission> allPermissions() {
                return applicationPermissionRepository.allPermissions();
        }
        //endregion


        //region > inject
        @Inject
        private ApplicationPermissionRepository applicationPermissionRepository;
        //endregion

}
