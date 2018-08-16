package org.incode.example.document.demo.shared.demowithurl;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.document.demo.shared.demowithurl.fixture.DocDemoObjectWithUrl_tearDown;

@XmlRootElement(name = "module")
public class DocumentDemoSharedDemoWithUrlSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DocDemoObjectWithUrl_tearDown();
    }
}
