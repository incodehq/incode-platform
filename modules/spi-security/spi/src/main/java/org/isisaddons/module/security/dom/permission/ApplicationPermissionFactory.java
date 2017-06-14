package org.isisaddons.module.security.dom.permission;


import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;

/**
 * Optional hook for alternative implementations of {@link org.isisaddons.module.security.dom.permission.ApplicationPermission}.
 *
 * <p>
 *     To use, simply implement the interface and annotate that implementation with {@link org.apache.isis.applib.annotation.DomainService},
 *     for example:
 * </p>
 * <pre>
 *     &#64;DomainService
 *     public class MyApplicationPermissionFactory implements ApplicationPermissionFactory {
 *         public ApplicationPermission newApplicationPermission() {
 *             return container.newTransientInstance(MyApplicationPermission.class);
 *         }
 *
 *         &#64;Inject
 *         DomainObjectContainer container;
 *     }
 * </pre>
 * <p>
 *     where:
 * </p>
 * <pre>
 *     public class MyApplicationPermission extends ApplicationPermission { ... }
 * </pre>
 */
public interface ApplicationPermissionFactory {

    public ApplicationPermission newApplicationPermission();

    public static class Default implements ApplicationPermissionFactory {

        public Default() {
            this(null);
        }
        Default(final DomainObjectContainer container) {
            this.container = container;
        }
        public ApplicationPermission newApplicationPermission() {
            return container.newTransientInstance(ApplicationPermission.class);
        }

        @Inject
        DomainObjectContainer container;

    }

}
