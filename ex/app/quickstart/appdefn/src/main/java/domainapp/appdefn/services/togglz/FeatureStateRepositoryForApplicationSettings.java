package domainapp.appdefn.services.togglz;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo;
import org.isisaddons.module.togglz.glue.spi.FeatureState;
import org.isisaddons.module.togglz.glue.spi.FeatureStateRepository;

/**
 * Implements the togglz SPI and delegates to estatio's own wrapper around the settings service.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class FeatureStateRepositoryForApplicationSettings implements FeatureStateRepository {

    static class FeatureStateForApplicationSetting implements FeatureState {

        static FeatureState from(final ApplicationSetting applicationSetting) {
            return applicationSetting != null ? new FeatureStateForApplicationSetting(applicationSetting) : null;
        }

        private final ApplicationSettingJdo applicationSetting;

        private FeatureStateForApplicationSetting(final ApplicationSetting applicationSetting) {
            this.applicationSetting = (ApplicationSettingJdo) applicationSetting;
        }

        @Override
        public String getValue() {
            return applicationSetting.valueAsString();
        }

        @Override
        public void setValue(final String value) {
            applicationSetting.updateAsString(value);
        }

    }

    @Override
    public FeatureState find(final String key) {
        final ApplicationSetting applicationSetting = applicationSettingsService.find(key);
        return FeatureStateForApplicationSetting.from(applicationSetting);
    }

    @Override
    public FeatureState create(final String key) {
        final ApplicationSetting applicationSetting = applicationSettingsService.newString(key, "", "");
        return FeatureStateForApplicationSetting.from(applicationSetting);
    }

    @Inject
    ApplicationSettingsServiceRW applicationSettingsService;

}

