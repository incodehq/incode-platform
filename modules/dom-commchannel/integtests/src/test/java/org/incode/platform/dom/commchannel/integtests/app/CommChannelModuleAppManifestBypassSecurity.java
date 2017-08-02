package org.incode.platform.dom.commchannel.integtests.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class CommChannelModuleAppManifestBypassSecurity extends CommChannelModuleAppManifest {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }

}
