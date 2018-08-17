package org.isisaddons.module.togglz.glue.service.staterepo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import org.togglz.core.Feature;
import org.togglz.core.repository.FeatureState;

/**
 * Intermediary between Togglz's {@link FeatureState} and the JSON that is stored as an {@link org.incode.module.settings.dom.ApplicationSetting} using the {@link org.incode.module.settings.dom.ApplicationSettingsService}.
 */
class FeatureStateJson {

    String name;
    String strategyId;
    boolean enabled;
    Map<String,String> parameters = new LinkedHashMap<>();

    /**
     * For GSON to deserialize from json.
     */
    public FeatureStateJson() {
    }

    /**
     * Convert from existing {@link FeatureState}.
     */
    FeatureStateJson(final FeatureState featureState) {
        name = featureState.getFeature().name();
        enabled = featureState.isEnabled();
        strategyId = featureState.getStrategyId();
        parameters = Maps.newLinkedHashMap(featureState.getParameterMap());
    }

    /**
     * Convert this json into a Togglz' {@link FeatureState}.
     */
    public FeatureState asFeatureState(final Feature feature) {
        final FeatureState featureState = new FeatureState(feature);
        featureState.setEnabled(enabled);
        for (Map.Entry<String, String> keyValue : parameters.entrySet()) {
            featureState.setParameter(keyValue.getKey(), keyValue.getValue());
        }
        featureState.setStrategyId(strategyId);
        return featureState;
    }
}
