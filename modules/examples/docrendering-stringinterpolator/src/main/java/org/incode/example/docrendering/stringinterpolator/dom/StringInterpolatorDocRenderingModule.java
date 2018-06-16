package org.incode.example.docrendering.stringinterpolator.dom;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;

import org.incode.example.document.dom.DocumentModule;

@XmlRootElement(name = "module")
public class StringInterpolatorDocRenderingModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new StringInterpolatorModule(),
                new DocumentModule()
        );
    }

}
