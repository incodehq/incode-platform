package org.incode.domainapp.extended.integtests.examples.country;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.country.CountryModule;

@XmlRootElement(name = "module")
public class CountryModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CountryModule()
        );
    }
}
