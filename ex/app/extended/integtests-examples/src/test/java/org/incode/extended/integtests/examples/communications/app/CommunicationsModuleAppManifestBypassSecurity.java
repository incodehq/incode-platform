package org.incode.extended.integtests.examples.communications.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class CommunicationsModuleAppManifestBypassSecurity extends CommunicationsModuleAppManifest {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }

}
