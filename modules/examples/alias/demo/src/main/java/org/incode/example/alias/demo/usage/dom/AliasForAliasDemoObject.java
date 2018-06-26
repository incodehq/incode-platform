package org.incode.example.alias.demo.usage.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;

import org.incode.example.alias.demo.shared.dom.AliasedObject;
import org.incode.example.alias.dom.impl.Alias;
import org.incode.example.alias.dom.impl.AliasRepository;
import org.incode.example.alias.dom.impl.T_addAlias;
import org.incode.example.alias.dom.impl.T_aliases;
import org.incode.example.alias.dom.impl.T_removeAlias;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="aliasDemoUsage"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class AliasForAliasDemoObject extends Alias {


    @Column(allowsNull = "false", name = "demoObjectId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private AliasedObject aliasedObject;


    @Override
    public Object getAliased() {
        return this.getAliasedObject();
    }
    @Override
    protected void setAliased(final Object aliased) {
        this.setAliasedObject((AliasedObject) aliased);
    }


    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends AliasRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(AliasedObject.class, AliasForAliasDemoObject.class);
        }
    }


    @Mixin
    public static class _aliases extends T_aliases<AliasedObject> {
        public _aliases(final AliasedObject aliased) {
            super(aliased);
        }
    }
    @Mixin
    public static class _addAlias extends T_addAlias<AliasedObject> {
        public _addAlias(final AliasedObject aliased) {
            super(aliased);
        }
    }
    @Mixin
    public static class _removeAlias extends T_removeAlias<AliasedObject> {
        public _removeAlias(final AliasedObject aliased) {
            super(aliased);
        }
    }

}
