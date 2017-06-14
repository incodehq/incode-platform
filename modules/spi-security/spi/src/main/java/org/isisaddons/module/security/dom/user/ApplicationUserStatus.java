package org.isisaddons.module.security.dom.user;

import org.apache.isis.core.commons.lang.StringExtensions;

/**
 * Whether the user's account is enabled or disabled.
 */
public enum ApplicationUserStatus {
    ENABLED,
    DISABLED;

    static ApplicationUserStatus parse(final Boolean enabled) {
        return enabled != null && enabled ? ENABLED : DISABLED;
    }

    @Override
    public String toString() {
        return StringExtensions.capitalize(name());
    }

}
