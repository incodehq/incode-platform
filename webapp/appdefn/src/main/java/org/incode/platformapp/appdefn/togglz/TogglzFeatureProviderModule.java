package org.incode.platformapp.appdefn.togglz;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.togglz.TogglzModule;

import org.incode.module.settings.SettingsModule;

@XmlRootElement(name = "module")
public class TogglzFeatureProviderModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new TogglzModule(),
                new SettingsModule()
        );
    }

}
