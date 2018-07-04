package org.isisaddons.module.stringinterpolator.dom;

import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class StringInterpolatorService {

    public static class Root {
        private final Object _this;
        private Map<String, String> properties;

        public Root(final Object context) {
            this._this = context;
        }

        public Object getThis() {
            return _this;
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        Root withProperties(Map<String, String> properties) {
            this.properties = properties;
            return this;
        }
    }

    private Map<String, String> properties;
    private boolean strict;

    @Programmatic
    @PostConstruct
    public void init(final Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * To assist in unit testing expressions.
     */
    @Programmatic
    public boolean isStrict() {
        return strict;
    }

    @Programmatic
    public StringInterpolatorService withStrict(boolean strict) {
        this.strict = strict;
        return this;
    }


    @Programmatic
    public String interpolate(final Object domainObject, final String template) {
        return interpolate(new Root(domainObject), template);
    }

    @Programmatic
    public String interpolate(final Root root, final String template) {
        final Root rootNoNull = root != null ? root : new Root(null);
        return new StringInterpolatorHelper(template, rootNoNull.withProperties(properties), strict).interpolate();
    }

}


