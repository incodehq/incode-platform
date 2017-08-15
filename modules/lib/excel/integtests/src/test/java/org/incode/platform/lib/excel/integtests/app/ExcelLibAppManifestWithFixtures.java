package org.incode.platform.lib.excel.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.todoitems.DemoToDoItem_recreate_usingExcelFixture;

public class ExcelLibAppManifestWithFixtures extends ExcelLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoToDoItem_recreate_usingExcelFixture.class);
    }


}
