package domainapp.appdefn;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import domainapp.modules.simple.SimpleModule;

@XmlRootElement(name = "module")
public class DomainAppAppDefnModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(new SimpleModule());
    }
}
