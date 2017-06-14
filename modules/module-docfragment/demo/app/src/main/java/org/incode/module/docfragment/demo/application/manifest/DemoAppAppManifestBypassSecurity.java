package org.incode.module.docfragment.demo.application.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DemoAppAppManifestBypassSecurity extends DemoAppAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
