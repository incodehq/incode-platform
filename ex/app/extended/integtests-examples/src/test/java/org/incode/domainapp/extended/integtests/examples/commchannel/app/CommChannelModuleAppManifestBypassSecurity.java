package org.incode.domainapp.extended.integtests.examples.commchannel.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class CommChannelModuleAppManifestBypassSecurity extends CommChannelModuleAppManifest {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }

}
