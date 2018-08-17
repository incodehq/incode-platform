package org.isisaddons.module.security.dom.user;

import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;

/**
 * Optional hook so that alternative implementations of {@link org.isisaddons.module.security.dom.user.ApplicationUser}.
 *
 * <p>
 *     To use, implement the interface and annotate that implementation with {@link org.apache.isis.applib.annotation.DomainService},
 *     for example:
 * </p>
 * <pre>
 *     &#64;DomainService
 *     public class MyApplicationUserFactory implements ApplicationUserFactory {
 *         public ApplicationUser newApplicationUser() {
 *             return container.newTransientInstance(MyApplicationUser.class);
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
 *     public class MyApplicationUser extends ApplicationUser { ... }
 * </pre>
 */
public interface ApplicationUserFactory {

    public ApplicationUser newApplicationUser();

    public static class Default implements ApplicationUserFactory {

        public Default() {
            this(null);
        }
        Default(final DomainObjectContainer container) {
            this.container = container;
        }
        public ApplicationUser newApplicationUser() {
            return container.newTransientInstance(ApplicationUser.class);
        }

        @Inject
        DomainObjectContainer container;

    }

}
