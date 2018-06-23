package org.incode.example.docfragment.demo.shared;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.docfragment.demo.shared.customer.DocFragmentDemoSharedCustomerSubmodule;
import org.incode.example.docfragment.demo.shared.invoicewithatpath.DocFragmentDemoSharedInvoiceWithAtPathSubmodule;

@XmlRootElement(name = "module")
public class DocFragmentDemoSharedModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocFragmentDemoSharedCustomerSubmodule(),
                new DocFragmentDemoSharedInvoiceWithAtPathSubmodule()
        );
    }
}
