package org.incode.module.errorrptslack;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.module.slack.SlackModule;

@XmlRootElement(name = "module")
public class ErrorReportingSlackModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new SlackModule()
        );
    }

}
