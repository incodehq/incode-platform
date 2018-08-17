package org.isisaddons.module.security.facets;

import org.apache.isis.applib.events.UsabilityEvent;
import org.apache.isis.applib.events.VisibilityEvent;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;
import org.apache.isis.applib.services.user.UserService;
import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facetapi.FacetAbstract;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.interactions.UsabilityContext;
import org.apache.isis.core.metamodel.interactions.VisibilityContext;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyEvaluator;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserRepository;

import java.util.List;
import java.util.concurrent.Callable;

public class TenantedAuthorizationFacetDefault extends FacetAbstract implements TenantedAuthorizationFacet {

    public static Class<? extends Facet> type() {
        return TenantedAuthorizationFacet.class;
    }

    private final List<ApplicationTenancyEvaluator> evaluators;
    private final ApplicationUserRepository applicationUserRepository;
    private final QueryResultsCache queryResultsCache;
    private final UserService userService;

    public TenantedAuthorizationFacetDefault(
            final List<ApplicationTenancyEvaluator> evaluators,
            final ApplicationUserRepository applicationUserRepository,
            final QueryResultsCache queryResultsCache,
            final UserService userService,
            final FacetHolder holder) {
        super(type(), holder, Derivation.NOT_DERIVED);
        this.evaluators = evaluators;
        this.applicationUserRepository = applicationUserRepository;
        this.queryResultsCache = queryResultsCache;
        this.userService = userService;
    }

    @Override
    public String hides(final VisibilityContext<? extends VisibilityEvent> ic) {

        if(evaluators == null || evaluators.isEmpty()) {
            return null;
        }

        final Object domainObject = ic.getTarget().getObject();
        final String userName = userService.getUser().getName();

        final ApplicationUser applicationUser = findApplicationUser(userName);
        if (applicationUser == null) {
            // not expected, but best to be safe...
            return "Could not locate application user for " + userName;
        }

        for (ApplicationTenancyEvaluator evaluator : evaluators) {
            final String reason = evaluator.hides(domainObject, applicationUser);
            if(reason != null) {
                return reason;
            }
        }
        return null;
    }


    @Override
    public String disables(final UsabilityContext<? extends UsabilityEvent> ic) {
        if(evaluators == null || evaluators.isEmpty()) {
            return null;
        }

        final Object domainObject = ic.getTarget().getObject();
        final String userName = userService.getUser().getName();

        final ApplicationUser applicationUser = findApplicationUser(userName);
        if (applicationUser == null) {
            // not expected, but best to be safe...
            return "Could not locate application user for " + userName;
        }

        for (ApplicationTenancyEvaluator evaluator : evaluators) {
            final String reason = evaluator.disables(domainObject, applicationUser);
            if(reason != null) {
                return reason;
            }
        }
        return null;
    }


    /**
     * Per {@link #findApplicationUserNoCache(String)}, cached for the request using the {@link QueryResultsCache}.
     */
    protected ApplicationUser findApplicationUser(final String userName) {
        return queryResultsCache.execute(new Callable<ApplicationUser>() {
            @Override
            public ApplicationUser call() throws Exception {
                return findApplicationUserNoCache(userName);
            }
        }, TenantedAuthorizationFacetDefault.class, "findApplicationUser", userName);
    }

    protected ApplicationUser findApplicationUserNoCache(final String userName) {
        return applicationUserRepository.findByUsername(userName);
    }

}
