package org.isisaddons.module.togglz.glue.spi;

import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.togglz.glue.service.staterepo.StateRepositoryDelegatingToSpiWithGson;

/**
 * As used by {@link StateRepositoryDelegatingToSpiWithGson}.
 */
public interface FeatureStateRepository {

    @Programmatic
    FeatureState find(String key);
    @Programmatic
    FeatureState create(String key);

}