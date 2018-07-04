package org.isisaddons.module.security.dom.password;

public class PasswordEncryptionServiceNoneTest extends PasswordEncryptionServiceContractTest {

    @Override
    protected PasswordEncryptionService newPasswordEncryptionService() {
        final PasswordEncryptionServiceNone service = new PasswordEncryptionServiceNone();
        return service;
    }

    @Override
    protected PasswordEncryptionService newPasswordEncryptionServiceDifferentSalt() {
        final PasswordEncryptionServiceNone service = new PasswordEncryptionServiceNone();
        return service;
    }
}