package org.isisaddons.wicket.pdfjs.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

@XmlRootElement(name = "module")
public class FixturesModuleWktPdfjsSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithBlob_tearDown();
    }

}
