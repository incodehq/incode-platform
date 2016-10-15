/**
 * Defines the {@link org.estatio.dom.EstatioImmutableObject reference data} {@link org.estatio.dom.geography.Geography}
 * entity, with concrete subtypes {@link org.incode.module.country.dom.impl.Country} and
 * {@link org.incode.module.country.dom.impl.State}.
 * 
 * <p>
 * A {@link org.incode.module.country.dom.impl.Country} consists of {@link org.incode.module.country.dom.impl.State}s, however this
 * relationship is performed through a 
 * {@link org.incode.module.country.dom.impl.Country_states#states(Country) contributed collection} rather than being a
 * mapped association.
 */
package org.incode.module.country.dom.impl;

import org.incode.module.country.dom.impl.Country;