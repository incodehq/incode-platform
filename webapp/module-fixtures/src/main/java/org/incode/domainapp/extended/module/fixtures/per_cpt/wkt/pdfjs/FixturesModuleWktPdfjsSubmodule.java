package org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.fixture.DemoObjectWithBlob_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleWktPdfjsSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithBlob_tearDown();
    }

}
