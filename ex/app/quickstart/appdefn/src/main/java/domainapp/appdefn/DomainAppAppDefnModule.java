package domainapp.appdefn;

import java.util.Set;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import domainapp.modules.simple.SimpleModule;

public class DomainAppAppDefnModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(new SimpleModule());
    }
}
