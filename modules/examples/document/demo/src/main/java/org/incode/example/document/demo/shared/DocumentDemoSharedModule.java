package org.incode.example.document.demo.shared;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.document.demo.shared.demowithnotes.DocumentDemoSharedDemoWithNotesSubmodule;
import org.incode.example.document.demo.shared.demowithurl.DocumentDemoSharedDemoWithUrlSubmodule;
import org.incode.example.document.demo.shared.other.DocumentDemoOtherSubmodule;

@XmlRootElement(name = "module")
public class DocumentDemoSharedModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocumentDemoSharedDemoWithNotesSubmodule(),
                new DocumentDemoSharedDemoWithUrlSubmodule(),
                new DocumentDemoOtherSubmodule()
        );
    }
}
