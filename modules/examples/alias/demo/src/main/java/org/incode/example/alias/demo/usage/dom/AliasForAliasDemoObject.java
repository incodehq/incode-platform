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

import org.incode.example.alias.demo.shared.dom.AliasDemoObject;
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
public class AliasForDemoObject extends Alias {


    @Column(allowsNull = "false", name = "demoObjectId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private AliasDemoObject aliasDemoObject;


    @Override
    public Object getAliased() {
        return this.getAliasDemoObject();
    }
    @Override
    protected void setAliased(final Object aliased) {
        this.setAliasDemoObject((AliasDemoObject) aliased);
    }


    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends AliasRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(AliasDemoObject.class, AliasForDemoObject.class);
        }
    }


    @Mixin
    public static class _aliases extends T_aliases<AliasDemoObject> {
        public _aliases(final AliasDemoObject aliased) {
            super(aliased);
        }
    }
    @Mixin
    public static class _addAlias extends T_addAlias<AliasDemoObject> {
        public _addAlias(final AliasDemoObject aliased) {
            super(aliased);
        }
    }
    @Mixin
    public static class _removeAlias extends T_removeAlias<AliasDemoObject> {
        public _removeAlias(final AliasDemoObject aliased) {
            super(aliased);
        }
    }

}
