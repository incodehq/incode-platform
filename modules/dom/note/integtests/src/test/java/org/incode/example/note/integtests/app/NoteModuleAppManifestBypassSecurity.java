package org.incode.example.note.integtests.app;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class NoteModuleAppManifestBypassSecurity extends NoteModuleAppManifest {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }

}
