/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.command.fixture.dom;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.background.BackgroundService2;
import org.apache.isis.applib.services.user.UserService;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class SomeCommandSubscriber extends AbstractSubscriber {

    @Subscribe
    public void on(SomeCommandAnnotatedObject.ChangeColorActionDomainEvent event) {

        if(!event.getEventPhase().isExecuted()) {
            return;
        }

        SomeCommandAnnotatedObject scao = event.getSource();
        scheduleBackgroundCommandsToCopyColourOver(scao);

    }

    @Subscribe
    public void on(SomeCommandAnnotatedObject_changeColorViaMixin.ActionDomainEvent event) {

        if(!event.getEventPhase().isExecuted()) {
            return;
        }

        SomeCommandAnnotatedObject scao = (SomeCommandAnnotatedObject) event.getMixedIn();
        scheduleBackgroundCommandsToCopyColourOver(scao);

    }

    void scheduleBackgroundCommandsToCopyColourOver(final SomeCommandAnnotatedObject scao) {
        // these will create new objects.
        backgroundService2.execute(scao).updateCopyOfColorUpdatedBySubscribedBackgroundDirectAction(scao.getColour());
        backgroundService2.executeMixin(SomeCommandAnnotatedObject_updateCopyOfColorUpdatedBySubscribedBackgroundMixinAction.class, scao).$$(scao.getColour());

        // not supported
        // backgroundService2.execute(scao).setCopyOfColorUpdatedBySubscribedBackgroundPropertyEdit(scao.getColour());
    }

    @Inject
    BackgroundService2 backgroundService2;
    @Inject
    UserService userService;

}
