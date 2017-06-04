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
package org.isisaddons.module.poly.fixture.dom.modules.casemgmt;

import javax.inject.Inject;
import com.google.common.eventbus.Subscribe;
import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CasePrimaryContentSubscriber extends AbstractSubscriber {

    /**
     * Cascade delete of the primary link
     */
    @Programmatic
    @Subscribe
    public void on(final CaseContentContributions.RemoveFromCaseDomainEvent ev) {
        switch (ev.getEventPhase()) {
            case EXECUTING:
                final CasePrimaryContentLink link = casePrimaryContentLinks.findByCaseAndContent(ev.getCase(), ev.getContent());
                if(link != null) {
                    container.remove(link);
                }
                break;
        }
    }

    @Inject
    private CasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    private DomainObjectContainer container;

}
