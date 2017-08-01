package org.incode.module.commchannel.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class CommChannelModuleAppManifestBypassSecurity extends CommChannelModuleAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
