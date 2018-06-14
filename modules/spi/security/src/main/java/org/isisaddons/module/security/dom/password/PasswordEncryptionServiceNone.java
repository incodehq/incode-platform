package org.isisaddons.module.security.dom.password;

import java.util.Objects;
import org.apache.isis.applib.annotation.Programmatic;

public class PasswordEncryptionServiceNone implements PasswordEncryptionService {

    @Programmatic
    @Override
    public String encrypt(String password) {
        return password;
    }

    @Programmatic
    @Override
    public boolean matches(String candidate, String encrypted) {
        return Objects.equals(candidate, encrypted);
    }

}
