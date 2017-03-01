/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.incode.module.communications.dom.impl.covernotes;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.eventbus.AbstractDomainEvent;

import org.incode.module.communications.dom.impl.comms.Communication;
import org.incode.module.communications.dom.mixins.Document_communicationAttachments;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentAbstract_attachedTo;
import org.incode.module.document.dom.impl.docs.Document_backgroundCommands;
import org.incode.module.document.dom.impl.docs.Document_supportingDocuments;

@DomainService(nature = NatureOfService.DOMAIN)
public class SuppressContributionsForCoverNoteDocument extends AbstractSubscriber {

    @EventHandler
    @Subscribe
    public void on(Document_communicationAttachments.ActionDomainEvent ev) {
        hideIfCoverNote(ev);
    }

    @EventHandler
    @Subscribe
    public void on(Document_backgroundCommands.ActionDomainEvent ev) {
        hideIfCoverNote(ev);
    }

    @EventHandler
    @Subscribe
    public void on(DocumentAbstract_attachedTo.ActionDomainEvent ev) {
        hideIfCoverNote(ev);
    }

    @EventHandler
    @Subscribe
    public void on(Document_supportingDocuments.ActionDomainEvent ev) {
        hideIfCoverNote(ev);
    }

    private void hideIfCoverNote(final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> ev) {
        if (ev.getEventPhase() != AbstractDomainEvent.Phase.HIDE) {
            return;
        }

        final Object mixedIn = ev.getMixedIn();
        if (!(mixedIn instanceof Document)) {
            return;
        }
        final Document document = (Document) mixedIn;

        final Communication communication = evaluator.coverNoteFor(document);
        if (communication != null) {
            ev.hide();
        }
    }

    @Inject
    Document_coverNoteFor.Evaluator evaluator;
}
