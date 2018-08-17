package org.isisaddons.module.security.dom.user;

import org.isisaddons.module.security.shiro.IsisModuleSecurityRealm;
import org.apache.isis.core.commons.lang.StringExtensions;

/**
 * Whether the user's account is local enabled (user/password) or 
 * delegated (eg LDAP), as per {@link IsisModuleSecurityRealm#setDelegateAuthenticationRealm(org.apache.shiro.realm.AuthenticatingRealm)}.
 */
public enum AccountType {
    LOCAL,
    DELEGATED;

    @Override
    public String toString() {
        return StringExtensions.capitalize(name());
    }

}
