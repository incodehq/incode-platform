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

    public String getId() {
        return "incodeCommChannel.LocationDereferencingServiceForCommunicationChannels";
    }


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
