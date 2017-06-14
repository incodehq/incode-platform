package org.incode.module.communications.demo.application.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DemoAppAppManifestWithFixturesBypassSecurity extends DemoAppAppManifestWithFixtures {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
