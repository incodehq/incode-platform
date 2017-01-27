package org.isisaddons.module.togglz.glue.service.staterepo;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.togglz.core.Feature;
import org.togglz.core.repository.StateRepository;

import org.apache.isis.core.runtime.system.context.IsisContext;

import org.isisaddons.module.togglz.TogglzModule;
import org.isisaddons.module.togglz.glue.spi.FeatureState;
import org.isisaddons.module.togglz.glue.spi.FeatureStateRepository;

public class StateRepositoryDelegatingToSpiWithGson implements StateRepository {

    private final Gson gson;

    public StateRepositoryDelegatingToSpiWithGson() {
        gson = new Gson();
    }

    @Override
    public org.togglz.core.repository.FeatureState getFeatureState(final Feature feature) {
        return IsisContext.getSessionFactory().doInSession(() -> {
            final FeatureState setting = findSetting(feature);
            if (setting == null) {
                return null;
            }

            final String json = setting.getValue();
            if(Strings.isNullOrEmpty(json)) {
                return null;
            }
            final FeatureStateJson featureStateJson =
                    gson.fromJson(json, FeatureStateJson.class);
            return featureStateJson.asFeatureState(feature);
        });
    }

    @Override
    public void setFeatureState(final org.togglz.core.repository.FeatureState featureState) {

        IsisContext.getSessionFactory().doInSession(() -> {
            final FeatureState setting = findSettingAutocreate(featureState);

            final FeatureStateJson featureStateJson = new FeatureStateJson(featureState);
            final String json = gson.toJson(featureStateJson);

            setting.setValue(json);
        });
    }

    private FeatureState findSetting(final Feature feature) {
        final String featureSettingKey = settingKeyFor(feature);
        final FeatureStateRepository featureStateRepository = lookupService(FeatureStateRepository.class);

        return featureStateRepository.find(featureSettingKey);
    }

    private FeatureState findSettingAutocreate(final org.togglz.core.repository.FeatureState featureState) {
        final String featureSettingKey = settingKeyFor(featureState.getFeature());

        final FeatureStateRepository featureStateRepository = lookupService(FeatureStateRepository.class);

        FeatureState setting = featureStateRepository.find(featureSettingKey);
        if (setting == null) {
            setting = featureStateRepository.create(featureSettingKey);
        }
        return setting;
    }

    private String settingKeyFor(final Feature feature) {
        return TogglzModule.class.getName() + "." + feature.name();
    }

    private <T> T lookupService(final Class<T> serviceClass) {
        return Util.lookupService(serviceClass);
    }

}
