package org.incode.module.classification.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class ClassificationModuleAppManifestBypassSecurity extends ClassificationModuleAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
