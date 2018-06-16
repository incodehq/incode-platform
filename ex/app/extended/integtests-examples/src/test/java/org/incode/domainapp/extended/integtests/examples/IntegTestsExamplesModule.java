package org.incode.domainapp.extended.integtests.examples;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.integtests.examples.alias.AliasModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.classification.ClassificationModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.commchannel.CommChannelModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.communications.CommunicationsModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.country.CountryModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.docfragment.DocFragmentModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.document.DocumentModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.note.NoteModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.settings.SettingsModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.tags.TagsModuleIntegTestAbstract;

@XmlRootElement(name = "module")
public class IntegTestsExamplesModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                AliasModuleIntegTestAbstract.module(),
                ClassificationModuleIntegTestAbstract.module(),
                CommChannelModuleIntegTestAbstract.module(),
                CommunicationsModuleIntegTestAbstract.module(),
                CountryModuleIntegTestAbstract.module(),
                DocFragmentModuleIntegTestAbstract.module(),
                DocumentModuleIntegTestAbstract.module(),
                NoteModuleIntegTestAbstract.module(),
                SettingsModuleIntegTestAbstract.module(),
                TagsModuleIntegTestAbstract.module()
        );
    }
}
