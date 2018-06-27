package org.incode.example.docfragment.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.example.docfragment.demo.shared.customer.DocFragCustomerSubmodule;
import org.incode.example.docfragment.demo.shared.invoicewithatpath.DocFragInvoiceSubmodule;
import org.incode.example.docfragment.DocFragmentModule;

@XmlRootElement(name = "module")
public class DocFragmentDemoUsageSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocFragmentModule(),
                new DocFragCustomerSubmodule(),
                new DocFragInvoiceSubmodule()
        );
    }

}
