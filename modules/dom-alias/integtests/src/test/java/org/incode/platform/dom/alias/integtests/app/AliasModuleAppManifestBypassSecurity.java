package org.incode.platform.dom.alias.integtests.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class AliasModuleAppManifestBypassSecurity extends AliasModuleAppManifest {

    @Override
    protected String overrideAuthMechanism() {
        return "bypass";
    }

}
