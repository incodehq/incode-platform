package org.isisaddons.wicket.excel.app;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.wicket.excel.fixture.ExcelWicketSetUpFixtureForSven;

public class DomainappAppManifestWithDemoFixture extends DomainappAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(ExcelWicketSetUpFixtureForSven.class);
    }


}
