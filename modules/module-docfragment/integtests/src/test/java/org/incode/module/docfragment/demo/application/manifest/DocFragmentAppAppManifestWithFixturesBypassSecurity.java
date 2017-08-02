package org.incode.module.docfragment.demo.application.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DocFragmentAppAppManifestWithFixturesBypassSecurity extends DocFragmentAppAppManifestWithFixtures {

    @Override
    protected String overrideAuthMechanism() {
        return "bypass";
    }

}
