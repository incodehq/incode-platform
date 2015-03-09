package domainapp.dom.modules.poly;

public abstract class PolymorphicLinkInstantiateEvent<S,PR,L> extends java.util.EventObject {

    private final Class<L> linkType;
    private final S subject;
    private final PR polymorphicReference;

    private Class<? extends L> subtype;

    public PolymorphicLinkInstantiateEvent(final Class<L> linkType, final Object source, final S subject, final PR polymorphicReference) {
        super(source);
        this.linkType = linkType;
        this.subject = subject;
        this.polymorphicReference = polymorphicReference;
    }

    public S getSubject() {
        return subject;
    }

    public PR getPolymorphicReference() {
        return polymorphicReference;
    }

    /**
     * For the factory (that will actually instantiate the link) to call.
     */
    public Class<? extends L> getSubtype() {
        return subtype;
    }

    /**
     * For the subscriber (that wishes to specify the subtype to use) to call.
     * @param subtype
     */
    public void setSubtype(final Class<? extends L> subtype) {
        if(this.subtype != null) {
            throw new IllegalArgumentException(String.format("A subtype ('%s') has already been set", subtype.getName()));
        }
        this.subtype = subtype;
    }
}
