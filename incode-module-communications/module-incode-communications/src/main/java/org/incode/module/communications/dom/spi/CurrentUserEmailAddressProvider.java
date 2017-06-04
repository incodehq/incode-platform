/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.communications.dom.spi;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.user.ApplicationUser;

public interface CurrentUserEmailAddressProvider {

    @Programmatic
    String currentUserEmailAddress();

    /**
     * Default implementation.
     */
    @DomainService(
            nature = NatureOfService.DOMAIN
    )
    public static class UsingMeService implements CurrentUserEmailAddressProvider {

        @Override
        public String currentUserEmailAddress() {
            if(meService == null) {
                throw new IllegalStateException("Security module has not been added to the AppManifest, and no other implementation of CurrentUserEmailAddressProvider has been supplied.");
            }
            final ApplicationUser currentUser = meService.me();
            return currentUser.getEmailAddress();
        }

        @Inject
        MeService meService;

    }

}
