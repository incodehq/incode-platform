package org.incode.example.commchannel.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

import org.incode.example.commchannel.demo.usage.CommChannelDemoUsageModule;
import org.incode.example.commchannel.dom.api.GeocodingService;

@XmlRootElement(name = "module")
public class CommChannelModuleIntegTestModule extends ModuleAbstract {

    public CommChannelModuleIntegTestModule() {
        withConfigurationProperty(GeocodingService.class.getCanonicalName() + ".demo", "true");
    }

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommChannelDemoUsageModule(),
                new Gmap3ApplibModule(),
                new Gmap3UiModule(),
                new FakeDataModule()
        );
    }
}
