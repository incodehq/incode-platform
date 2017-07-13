package org.incode.module.docfragment.demo.application.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DocFragmentAppAppManifestBypassSecurity extends DocFragmentAppAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
