package org.incode.module.alias.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class AliasModuleAppManifestBypassSecurity extends AliasModuleAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
