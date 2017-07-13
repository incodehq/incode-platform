package org.incode.module.alias.fixture.app.alias;

import org.apache.isis.applib.annotation.*;
import org.incode.module.alias.dom.impl.*;
import org.incode.module.alias.fixture.dom.demo.DemoObject;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="incodeAliasDemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class AliasForDemoObject extends Alias {

    //region > demoObject (property)
    private DemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public DemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final DemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > aliased (hook, derived)
    @Override
    public Object getAliased() {
        return getDemoObject();
    }

    @Override
    protected void setAliased(final Object aliased) {
        setDemoObject((DemoObject) aliased);
    }
    //endregion

    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends AliasRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DemoObject.class, AliasForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _aliases extends T_aliases<DemoObject> {
        public _aliases(final DemoObject aliased) {
            super(aliased);
        }
    }

    @Mixin
    public static class _addAlias extends T_addAlias<DemoObject> {
        public _addAlias(final DemoObject aliased) {
            super(aliased);
        }
    }

    @Mixin
    public static class _removeAlias extends T_removeAlias<DemoObject> {
        public _removeAlias(final DemoObject aliased) {
            super(aliased);
        }
    }

    //endregion

}
