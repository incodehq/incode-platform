package org.incode.module.classification.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class ClassificationModuleAppManifestBypassSecurity extends ClassificationModuleAppManifest {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }
}
