package org.incode.domainapp.extended.integtests.examples.docfragment.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DocFragmentAppAppManifestWithFixturesBypassSecurity extends DocFragmentAppAppManifestWithFixtures {

    @Override
    protected String overrideAuthMechanism() {
        return "bypass";
    }

}
