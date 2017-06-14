package org.isisaddons.module.security.dom.password;

import org.mindrot.jbcrypt.BCrypt;

import org.apache.isis.applib.annotation.Programmatic;

public class PasswordEncryptionServiceUsingJBcrypt implements PasswordEncryptionService {

    String salt;

    private String getSalt() {
        if (salt == null) {
            salt = BCrypt.gensalt();
        }
        return salt;
    }

    @Programmatic
    @Override
    public String encrypt(String password) {
        return password == null ? null : BCrypt.hashpw(password, getSalt());
    }

    @Programmatic
    @Override
    public boolean matches(String candidate, String encrypted) {
        if (candidate == null && encrypted == null) {
            return true;
        }
        if (candidate == null || encrypted == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encrypted);
    }
}
