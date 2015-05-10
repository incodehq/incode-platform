package org.isisaddons.module.togglz.glue.spi;

import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.spi.FeatureManagerProvider;
import org.togglz.core.user.UserProvider;

/**
 * Subclass and register in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html.
 */
public abstract class TogglzModuleFeatureManagerProviderAbstract implements FeatureManagerProvider {

    protected static FeatureManager featureManager;
    protected final Class<? extends Feature>[] featureEnumClass;

    public TogglzModuleFeatureManagerProviderAbstract(
            final Class<? extends Feature> featureEnum,
            final Class<? extends Feature>... additionalFeatureEnum) {

        this.featureEnumClass = new Class[1 + additionalFeatureEnum.length];
        int i = 0;
        this.featureEnumClass[i++] = featureEnum;
        for (Class<? extends Feature> eachAdditionalFeatureEnum : additionalFeatureEnum) {
            this.featureEnumClass[i++] = eachAdditionalFeatureEnum;
        }
    }

    public static void setFeatureManager(FeatureManager featureManager) {
        TogglzModuleFeatureManagerProviderAbstract.featureManager = featureManager;
    }

    @Override
    public int priority() {
        return 25;
    }

    /**
     * Implementation that returns the cached {@link FeatureManager}, using {@link #newFeatureManagerBuilder()} to create
     * a builder for one when called the first time.
     */
    @Override
    public synchronized FeatureManager getFeatureManager() {
        if (featureManager == null) {
            featureManager = newFeatureManagerBuilder().build();
        }
        return featureManager;
    }

    /**
     * Overriddable (hook) factory method for the {@link FeatureManagerBuilder}.
     *
     * <p>
     *     Default implementation uses the {@link StateRepository} instantiated by the
     *     {@link #newStateRepository()} hook method and the {@link org.togglz.core.user.UserProvider}
     *     instantiated by the {@link #newUserProvider()} hook method.
     * </p>
     */
    protected FeatureManagerBuilder newFeatureManagerBuilder() {
        return new FeatureManagerBuilder()
                .featureEnums(getFeatureEnumClass())
                .stateRepository(newStateRepository())
                .userProvider(newUserProvider());
    }

    /**
     * Overridable (hook) factory method for the {@link StateRepository}.
     *
     * <p>
     *     Default implementation instantiates a {@link StateRepositoryUsingApplicationSettingsWithGson}.
     * </p>
     */
    protected StateRepository newStateRepository() {
        return new StateRepositoryUsingApplicationSettingsWithGson();
    }

    /**
     * Overridable (hook) factory method for the {@link UserProvider}.
     *
     * <p>
     *     Default implementation instantiates a {@link UserProviderUsingServletPrincipal}.
     * </p>
     */
    protected UserProvider newUserProvider() {
        return new UserProviderUsingServletPrincipal();
    }

    /**
     * As provided to the {@link #TogglzModuleFeatureManagerProviderAbstract(Class,Class[]) constructor}.
     */
    protected Class<? extends Feature>[] getFeatureEnumClass() {
        return featureEnumClass;
    }
}