package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.module.fixtures.shared.reminder.FixturesModuleSharedReminderSubmodule;

@XmlRootElement(name = "module")
public class FixturesModuleLibStringInterpolatorSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new FixturesModuleSharedReminderSubmodule());
    }
}
