package org.incode.module.document.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DocumentModuleAppManifestBypassSecurity extends DocumentModuleAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
