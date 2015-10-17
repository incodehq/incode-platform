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
package org.incode.module.commchannel.dom.impl.gmap3;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.wicket.gmap3.cpt.applib.LocationDereferencingService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_owner;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class LocationDereferencingServiceForCommunicationChannels implements LocationDereferencingService {

    //region > injected services
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > mixins
    private CommunicationChannel_owner owner(final CommunicationChannel communicationChannel) {
        return container.mixin(CommunicationChannel_owner.class, communicationChannel);
    }
    //endregion

    @Programmatic
	public Object dereference(final Object locatable) {
		if (!(locatable instanceof CommunicationChannel)) {
			return null;
		}
		final CommunicationChannel communicationChannel = (CommunicationChannel) locatable;
		return owner(communicationChannel).$$();
	}


}
