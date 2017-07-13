package org.incode.platform.demo.webapp.app.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DomainAppAppManifestWithFixturesBypassSecurity extends DomainAppAppManifestWithFixtures {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
