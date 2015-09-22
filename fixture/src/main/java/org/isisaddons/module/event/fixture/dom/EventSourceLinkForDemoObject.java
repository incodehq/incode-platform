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
package org.isisaddons.module.event.fixture.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.event.dom.EventSource;
import org.isisaddons.module.event.dom.EventSourceLink;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="eventdemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "eventdemo.EventSourceLinkForDemoObject"
)
public class EventSourceLinkForDemoObject extends EventSourceLink {

    //region > instantiationSubscriber, setPolymorphicReference
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class InstantiationSubscriber extends AbstractSubscriber {

        @Programmatic
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof EventDemoObject) {
                ev.setSubtype(EventSourceLinkForDemoObject.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final EventSource polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setDemoObject((EventDemoObject) polymorphicReference);
    }
    //endregion

    //region > demoObject (property)
    private EventDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    public EventDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final EventDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}
