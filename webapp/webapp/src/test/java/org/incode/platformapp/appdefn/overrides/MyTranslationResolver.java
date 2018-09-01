package org.incode.platformapp.appdefn.overrides;

import java.util.List;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.i18n.TranslationsResolver;

@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "100")
public class MyTranslationResolver implements TranslationsResolver {
    public static class Module extends ModuleAbstract {
    }
    @Override public List<String> readLines(final String s) {
        return null;
    }
}
