package org.isisaddons.module.security.dom.permission;

import org.apache.isis.core.commons.lang.StringExtensions;

/**
 * Named after UNIX modes (<code>chmod</code> etc), determines that nature of access (of denial of access if vetoed)
 * to an {@link ApplicationFeature}.
 */
public enum ApplicationPermissionMode {
    /**
     * Whether the user/role can view (or is prevented from viewing) the application feature (class member).
     *
     * <p>
     * The {@link ApplicationPermissionRule rule} of the
     * {@link org.isisaddons.module.security.dom.permission.ApplicationPermission} indicates whether access is being
     * granted or denied.
     * </p>
     */
    VIEWING,
    /**
     * Whether can user/role can change (or is prevented from changing) the state of the system using the application feature (class member).
     *
     * <p>
     * In other words, whether they can execute (if an action, modify/clear (if a property), addTo/removeFrom
     * (if a collection).
     * </p>
     *
     * <p>
     * The {@link ApplicationPermissionRule rule} of the
     * {@link org.isisaddons.module.security.dom.permission.ApplicationPermission} indicates whether access is being
     * granted or denied.
     * </p>
     *
     * <p>
     * Note that the Wicket viewer does not at the time of writing (Isis 1.6.0) support the notion of
     * mutable collections.  The RO viewer does, however.
     * </p>
     */
    CHANGING;

    @Override
    public String toString() {
        return StringExtensions.capitalize(name());
    }

}
