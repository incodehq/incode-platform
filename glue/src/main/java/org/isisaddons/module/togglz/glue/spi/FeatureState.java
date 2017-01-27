package org.isisaddons.module.togglz.glue.spi;

import org.apache.isis.applib.annotation.Programmatic;

public interface FeatureState {
    @Programmatic
    String getValue();
    @Programmatic
    void setValue(String value);
}