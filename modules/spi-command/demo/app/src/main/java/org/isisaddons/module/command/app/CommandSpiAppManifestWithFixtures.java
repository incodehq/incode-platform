package org.isisaddons.module.command.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.command.fixture.SomeCommandAnnotatedObjectsFixture;

public final class CommandSpiAppManifestWithFixtures extends CommandSpiAppManifest {

    public List<Class<? extends FixtureScript>> getFixtures() {
        return (List)Lists.newArrayList(SomeCommandAnnotatedObjectsFixture.class);
    }

    @Override
    public Map<String, String> getConfigurationProperties() {
        HashMap<String,String> props = Maps.newHashMap();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
