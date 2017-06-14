package org.isisaddons.module.security.dom.password;

import org.apache.isis.applib.annotation.Programmatic;

public interface PasswordEncryptionService {

    @Programmatic
    public String encrypt(final String password);

    @Programmatic
    public boolean matches(final String candidate, final String encrypted);
}
