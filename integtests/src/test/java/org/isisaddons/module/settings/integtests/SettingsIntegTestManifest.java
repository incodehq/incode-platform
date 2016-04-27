package org.isisaddons.module.settings.integtests;

import java.util.List;

import com.google.common.collect.Lists;

import org.isisaddons.module.settings.app.SettingsAppManifest;

public class SettingsIntegTestManifest
        extends SettingsAppManifest {

    @Override public List<Class<?>> getAdditionalServices() {
        final List classes = Lists.newArrayList(RegisterImplementationsDummyService.class);
        return classes;
    }
}
