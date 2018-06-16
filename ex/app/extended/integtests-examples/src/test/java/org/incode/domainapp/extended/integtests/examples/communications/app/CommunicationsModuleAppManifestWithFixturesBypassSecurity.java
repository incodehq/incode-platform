package org.incode.domainapp.extended.integtests.examples.communications.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class CommunicationsModuleAppManifestWithFixturesBypassSecurity
        extends CommunicationsModuleAppManifestWithFixtures {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }

}