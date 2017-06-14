package org.isisaddons.module.command.dom;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

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
    public List<CommandJdo> findByTransactionId(final UUID transactionId) {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class,
                        "findBackgroundCommandByTransactionId",
                        "transactionId", transactionId));
    }

    @Programmatic
    public List<CommandJdo> findByParent(CommandJdo parent) {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class,
                        "findBackgroundCommandsByParent",
                        "parent", parent));
    }

    @Programmatic
    public List<CommandJdo> findBackgroundCommandsNotYetStarted() {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class,
                        "findBackgroundCommandsNotYetStarted"));
    }

    @Inject
    RepositoryService repositoryService;
}
