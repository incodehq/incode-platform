package org.isisaddons.module.security.facets;

import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.interactions.DisablingInteractionAdvisor;
import org.apache.isis.core.metamodel.interactions.HidingInteractionAdvisor;

/**
 * Optionally hide or disable an object, property, collection or action
 * depending on the tenancy.
 */
public interface TenantedAuthorizationFacet extends Facet, HidingInteractionAdvisor, DisablingInteractionAdvisor {

}
