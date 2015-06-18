package org.isisaddons.module.publishmq.dom;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Prototyping",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "300"

)
public class PublishMqMenuService {

    private final static Logger LOG = LoggerFactory.getLogger(PublishMqMenuService.class);

    @Inject
    private PublishingServiceUsingMqEmbedded publishingServiceUsingActiveMqRa;

    @Action(
            restrictTo = RestrictTo.PROTOTYPING
    )
    public void publishMessage(
            @ParameterLayout(named="Message")
            final String messageStr,
            @ParameterLayout(named="Message Id")
            final String messageId) {
        publishingServiceUsingActiveMqRa.publishTextMessage(messageStr, messageId);
    }

}
