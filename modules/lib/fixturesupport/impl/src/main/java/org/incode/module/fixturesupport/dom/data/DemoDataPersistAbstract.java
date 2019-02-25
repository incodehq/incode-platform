package org.incode.module.fixturesupport.dom.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.registry.ServiceRegistry;

import lombok.Getter;

/**
 *
 * @deprecated - instead use {@link org.apache.isis.applib.fixturescripts.PersonaWithBuilderScript}
 */
@Deprecated
public abstract class DemoDataPersistAbstract<S extends DemoDataPersistAbstract<S, D, T>, D extends DemoData<D,T>, T> extends FixtureScript {

    private final Class<D> demoDataClass;

    protected DemoDataPersistAbstract(final Class<D> demoDataClass) {
        this.demoDataClass = demoDataClass;
    }

    /**
     * The number of objects to create.
     */
    @Getter
    private Integer number;
    public S setNumber(final Integer number) {
        this.number = number;
        return (S)this;
    }

    /**
     * The objects created by this fixture (output).
     */
    @Getter
    private final List<T> objects = new ArrayList<>();


    @Override
    protected void execute(final ExecutionContext ec) {

        final D[] enumConstants = demoDataClass.getEnumConstants();
        final int max = enumConstants.length;

        // defaults
        final int number = defaultParam("number", ec, max);

        // validate
        if(number < 0 || number > max) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", max));
        }

        for (int i = 0; i < number; i++) {
            final T domainObject = enumConstants[i].persistUsing(serviceRegistry);
            ec.addResult(this, domainObject);
            objects.add(domainObject);
        }
    }

    @javax.inject.Inject
    ServiceRegistry serviceRegistry;

}
