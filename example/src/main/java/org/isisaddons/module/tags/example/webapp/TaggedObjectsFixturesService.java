/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.tags.example.webapp;

import java.util.List;
import org.isisaddons.module.tags.example.fixture.ExampleTaggableEntitiesAppSetUpFixture;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.fixturescripts.SimpleFixtureScript;

/**
 * Enables fixtures to be installed from the application.
 */
@DomainService(menuOrder = "20")
@Named("Prototyping")
public class TaggedObjectsFixturesService extends FixtureScripts {

    public TaggedObjectsFixturesService() {
        super("org.isisaddons.module.tags.example.fixture");
    }

    //@Override // compatibility with core 1.5.0
    public FixtureScript default0RunFixtureScript() {
        return findFixtureScriptFor(SimpleFixtureScript.class);
    }

    /**
     * Raising visibility to <tt>public</tt> so that choices are available for first param
     * of {@link #runFixtureScript(FixtureScript, String)}.
     */
    @Override
    public List<FixtureScript> choices0RunFixtureScript() {
        return super.choices0RunFixtureScript();
    }


    // //////////////////////////////////////


    @Prototype
    @MemberOrder(sequence="20")
    public Object installFixturesAndReturnFirst() {
        final List<FixtureResult> run = findFixtureScriptFor(ExampleTaggableEntitiesAppSetUpFixture.class).run(null);
        return run.get(0).getObject();
    }





//    //
//    // reinstated because FixtureScripts doesn't seem to find our fixture ? ISIS-836
//    //
//
//    @Prototype
//    public String installFixtures() {
//        final FixturesInstallerDelegate installer = new FixturesInstallerDelegate().withOverride();
//        installer.addFixture(new ExampleTaggableEntitiesAppSetUpFixture());
//        installer.installFixtures();
//        return "Example fixtures installed";
//    }
//
//    // //////////////////////////////////////
//
//    @Prototype
//    public ExampleTaggableEntity installFixturesAndThenReturnFirst() {
//        installFixtures();
//        List<ExampleTaggableEntity> all = exampleTaggableEntities.listAll();
//        return !all.isEmpty() ? all.get(0) : null;
//    }
//
//
//    // //////////////////////////////////////
//
//    @javax.inject.Inject
//    private ExampleTaggableEntities exampleTaggableEntities;

}
