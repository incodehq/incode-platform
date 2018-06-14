package org.isisaddons.module.command.dom;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

/**
 * Provides supporting functionality for querying
 * {@link CommandJdo command} entities that have been persisted
 * to execute in the background.
 *
 * <p>
 * This supporting service with no UI and no side-effects, and is there are no other implementations of the service,
 * thus has been annotated with {@link org.apache.isis.applib.annotation.DomainService}.  This means that there is no
 * need to explicitly register it as a service (eg in <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class BackgroundCommandServiceJdoRepository {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(BackgroundCommandServiceJdoRepository.class);

    @Programmatic
    public List<CommandJdo> findByParent(CommandJdo parent) {
        return commandServiceRepository.findBackgroundCommandsByParent(parent);
    }

    @Programmatic
    public List<CommandJdo> findBackgroundCommandsNotYetStarted() {
        return commandServiceRepository.findBackgroundCommandsNotYetStarted();
    }

    @Inject
    CommandServiceJdoRepository commandServiceRepository;

}
