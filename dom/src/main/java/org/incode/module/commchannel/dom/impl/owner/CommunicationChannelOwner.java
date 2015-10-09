/*
 *
 *  Copyright 2015 incode.org
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
package org.incode.module.commchannel.dom.impl.owner;

import org.apache.isis.applib.Identifier;

import org.incode.module.commchannel.dom.CommChannelModule;

public interface CommunicationChannelOwner  {

    public static abstract class PropertyDomainEvent<S,T> extends
            CommChannelModule.PropertyDomainEvent<S, T> {
        public PropertyDomainEvent(final S source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<S,T> extends CommChannelModule.CollectionDomainEvent<S, T> {
        public CollectionDomainEvent(final S source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> {
        public ActionDomainEvent(final S source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

}
