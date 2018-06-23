package org.incode.example.alias.demo.examples.docfragment;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.examples.commchannel.demo.shared.customer.FixturesModuleSharedCustomerSubmodule;
import org.incode.examples.commchannel.demo.shared.invoicewithatpath.FixturesModuleSharedInvoiceWithAtPathSubmodule;
import org.incode.example.docfragment.DocFragmentModule;

@XmlRootElement(name = "module")
public class FixturesModuleExamplesDocFragmentIntegrationSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocFragmentModule(),
                new FixturesModuleSharedCustomerSubmodule(),
                new FixturesModuleSharedInvoiceWithAtPathSubmodule()
        );
    }

}
