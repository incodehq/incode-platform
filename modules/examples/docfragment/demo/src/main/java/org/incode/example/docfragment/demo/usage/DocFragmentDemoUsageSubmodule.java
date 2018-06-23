package org.incode.example.docfragment.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.docfragment.demo.shared.customer.DocFragmentDemoSharedCustomerSubmodule;
import org.incode.example.docfragment.demo.shared.invoicewithatpath.DocFragmentDemoSharedInvoiceWithAtPathSubmodule;
import org.incode.example.docfragment.DocFragmentModule;

@XmlRootElement(name = "module")
public class DocFragmentDemoUsageSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocFragmentModule(),
                new DocFragmentDemoSharedCustomerSubmodule(),
                new DocFragmentDemoSharedInvoiceWithAtPathSubmodule()
        );
    }

}
