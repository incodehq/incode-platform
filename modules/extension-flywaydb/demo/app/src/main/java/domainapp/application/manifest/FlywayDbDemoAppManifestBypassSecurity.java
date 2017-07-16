package domainapp.application.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class FlywayDbDemoAppManifestBypassSecurity extends FlywayDbDemoAppManifest {

    @Override
    public String getAuthenticationMechanism() {
        return "bypass";
    }

    @Override
    public String getAuthorizationMechanism() {
        return "bypass";
    }
}
