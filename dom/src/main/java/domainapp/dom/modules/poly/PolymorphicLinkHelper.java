package domainapp.dom.modules.poly;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.NonRecoverableException;
import org.apache.isis.applib.services.eventbus.EventBusService;

public class PolymorphicLinkHelper<S,PR,L extends SubjectPolymorphicReferenceLink<S,PR,L>,E extends PolymorphicLinkInstantiateEvent<S,PR,L>> {

    private final Object factory;
    private final Class<E> eventType;
    private final Class<L> linkType;
    private final Class<S> subjectType;
    private final Class<PR> polymorphicReferenceType;

    final Constructor<E> eventConstructor;

    public PolymorphicLinkHelper(
            final Object factory,
            final Class<S> subjectType,
            final Class<PR> polymorphicReferenceType,
            final Class<L> linkType, final Class<E> eventType) {
        this.factory = factory;
        this.subjectType = subjectType;
        this.polymorphicReferenceType = polymorphicReferenceType;
        this.eventType = eventType;
        this.linkType = linkType;

        try {
            eventConstructor = eventType.getConstructor(Object.class, subjectType, polymorphicReferenceType);
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format(
                    "Could not locate constructor in eventType '%s' accepting (%s, %s, %s)",
                    eventType.getName(),
                    Object.class.getName(),
                    subjectType.getName(),
                    polymorphicReferenceType.getName()));
        }
    }

    public void createLink(final S subject, final PR polymorphicReference) {

        final E event = instantiateEvent(factory, subject, polymorphicReference);
        eventBusService.post(event);

        final Class<? extends L> subtype = event.getSubtype();
        if(subtype == null) {
            throw new NonRecoverableException("Cannot create link to " + container.titleOf(polymorphicReference) + ", no subtype provided");
        }

        final L ownerLink = container.newTransientInstance(subtype);
        ownerLink.setPolymorphicReference(polymorphicReference);

        ownerLink.setSubject(subject);
        container.persist(ownerLink);
    }

    private E instantiateEvent(final Object factory, final S subject, final PR polymorphicReference) {
        try {
            return eventConstructor.newInstance(factory, subject, polymorphicReference);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    DomainObjectContainer container;
    @Inject
    EventBusService eventBusService;
}
