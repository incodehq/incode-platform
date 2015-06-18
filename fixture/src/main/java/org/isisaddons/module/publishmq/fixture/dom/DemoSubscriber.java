/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.publishmq.fixture.dom;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.publishmq.dom.PublishingServiceUsingMqEmbedded;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PublishMqDemoObject.class
)
@DomainServiceLayout(
        named = "Subscribers",
        menuOrder = "10",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY
)
public class DemoSubscriber implements MessageListener {



    private PublishingServiceUsingMqEmbedded.Handle handle;


    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @MemberOrder(sequence = "1")
    public void listen() {
        try {
            handle = publishingService.listen(this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String disableListen() {
        return handle != null? "Already listening": null;
    }


    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @MemberOrder(sequence = "2")
    public void unlisten() {
        publishingService.unlisten(handle);
    }

    public String disableUnlisten() {
        return handle == null? "Not yet listening": null;
    }



    private List<MessageViewModel> received = Lists.newArrayList();


    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "3")
    public List<MessageViewModel> received() {
        return received;
    }



    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @MemberOrder(sequence = "4")
    public void clear() {
        received.clear();
    }



    @Programmatic
    @Override
    public void onMessage(final Message message) {
        try {
            message.acknowledge();

            final MessageViewModel messageViewModel = new MessageViewModel();
            received.add(messageViewModel);
            messageViewModel.setCorrelationId(message.getJMSMessageID());
            messageViewModel.setXml(((TextMessage)message).getText());
        } catch (JMSException e) {
            // TODO: log this; anything else?
        }
    }


    //region > injected services

    @javax.inject.Inject
    PublishingServiceUsingMqEmbedded publishingService;

    @javax.inject.Inject
    DomainObjectContainer container;


    //endregion

}
