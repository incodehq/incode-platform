package org.incode.extended.integtests.examples.communications.app.services.fakecueap;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.user.UserService;

import org.incode.example.communications.dom.spi.CurrentUserEmailAddressProvider;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "incodeCommunicationsDemo.FakeCurrentUserEmailAddressProvider",
        menuOrder = "1"
)
@DomainServiceLayout(
        named = "Emails (Fake Server)"
)
public class FakeCurrentUserEmailAddressProvider implements CurrentUserEmailAddressProvider {

    @Override
    public String currentUserEmailAddress() {
        return userService.getUser().getName() + "@gmail.com";
    }

    @Inject
    UserService userService;

}
