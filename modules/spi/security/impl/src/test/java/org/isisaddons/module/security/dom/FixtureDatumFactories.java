package org.isisaddons.module.security.dom;

import com.danhaywood.java.testsupport.coverage.PojoTester;

import org.joda.time.LocalDate;

import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeature;
import org.apache.isis.core.metamodel.services.appfeat.ApplicationFeatureId;

import org.isisaddons.module.security.dom.permission.ApplicationPermission;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.ApplicationUser;

public class FixtureDatumFactories {

    public FixtureDatumFactories(){}

    public static PojoTester.FixtureDatumFactory<LocalDate> dates() {
        return new PojoTester.FixtureDatumFactory<>(LocalDate.class, null, new LocalDate(2012, 7, 19), new LocalDate(2012, 7, 20), new LocalDate(2012, 8, 19), new LocalDate(2013, 7, 19));
    }

    public static PojoTester.FixtureDatumFactory<Boolean> booleans() {
        return new PojoTester.FixtureDatumFactory<>(Boolean.class, null, Boolean.FALSE, Boolean.TRUE);
    }

    public static PojoTester.FixtureDatumFactory<ApplicationFeatureId> featureIds() {
        return new PojoTester.FixtureDatumFactory<>(ApplicationFeatureId.class, ApplicationFeatureId.newPackage("com.mycompany"), ApplicationFeatureId.newClass("com.mycompany.Foo"), ApplicationFeatureId.newMember("com.mycompany.Foo", "bar"));
    }

    public static PojoTester.FixtureDatumFactory<ApplicationRole> roles() {
        return new PojoTester.FixtureDatumFactory<>(ApplicationRole.class, new ApplicationRole(), new ApplicationRole());
    }

    public static PojoTester.FixtureDatumFactory<ApplicationUser> users() {
        return new PojoTester.FixtureDatumFactory<>(ApplicationUser.class, new ApplicationUser(), new ApplicationUser());
    }

    public static PojoTester.FixtureDatumFactory<ApplicationPermission> permissions() {
        return new PojoTester.FixtureDatumFactory<>(ApplicationPermission.class, new ApplicationPermission(), new ApplicationPermission());
    }

    public static PojoTester.FixtureDatumFactory<ApplicationTenancy> tenancies() {
        return new PojoTester.FixtureDatumFactory<>(ApplicationTenancy.class, new ApplicationTenancy(), new ApplicationTenancy());
    }

    public static PojoTester.FixtureDatumFactory<ApplicationFeature> features() {
        return new PojoTester.FixtureDatumFactory<>(ApplicationFeature.class, new ApplicationFeature(), new ApplicationFeature());
    }

}
