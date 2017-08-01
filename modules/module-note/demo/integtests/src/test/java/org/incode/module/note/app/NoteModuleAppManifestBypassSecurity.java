package org.incode.module.note.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class NoteModuleAppManifestBypassSecurity extends NoteModuleAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
