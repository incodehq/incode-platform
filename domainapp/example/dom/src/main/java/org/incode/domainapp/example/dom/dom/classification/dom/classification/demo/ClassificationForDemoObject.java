package org.incode.domainapp.example.dom.dom.classification.dom.classification.demo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;

import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.impl.classification.T_classifications;
import org.incode.module.classification.dom.impl.classification.T_classify;
import org.incode.module.classification.dom.impl.classification.T_unclassify;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;

import org.incode.domainapp.example.dom.dom.classification.dom.demo.DemoObject;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema ="incodeClassificationDemo")
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "incodeClassificationDemo.ClassificationForDemoObject"
)
public class ClassificationForDemoObject extends Classification {

    //region > demoObject (property)
    private DemoObject demoObject;

    @Column(allowsNull = "false", name = "demoObjectId")
    @Property(editing = Editing.DISABLED)
    public DemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final DemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > classified (hook, derived)
    @Override
    public Object getClassified() {
        return getDemoObject();
    }

    @Override
    protected void setClassified(final Object classified) {
        setDemoObject((DemoObject) classified);
    }
    //endregion

    //region > ApplicationTenancyService SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class ApplicationTenancyServiceForDemoObject implements ApplicationTenancyService {
        @Override
        public String atPathFor(final Object domainObjectToClassify) {
            if(domainObjectToClassify instanceof DemoObject) {
                return ((DemoObject) domainObjectToClassify).getAtPath();
            }
            return null;
        }
    }
    //endregion

    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends ClassificationRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DemoObject.class, ClassificationForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _classifications extends T_classifications<DemoObject> {
        public _classifications(final DemoObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class _classify extends T_classify<DemoObject> {
        public _classify(final DemoObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class _unclassify extends T_unclassify<DemoObject> {
        public _unclassify(final DemoObject classified) {
            super(classified);
        }
    }

    //endregion

}
