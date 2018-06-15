package org.incode.extended.integtests.per_cpt.examples.communications.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class CommunicationsModuleAppManifestWithFixturesBypassSecurity
        extends CommunicationsModuleAppManifestWithFixtures {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }

}