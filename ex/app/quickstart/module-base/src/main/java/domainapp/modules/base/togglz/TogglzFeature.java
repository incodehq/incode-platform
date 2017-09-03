package domainapp.modules.base.togglz;

import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum TogglzFeature implements org.togglz.core.Feature {

    @Label("Enable SimpleObject#create")
    @EnabledByDefault
    SimpleObject_create,

    @Label("Enable SimpleObject#findByName")
    SimpleObject_findByName;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}
