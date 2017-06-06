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
package domainapp.application.services.dbmanager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.jdo.JDOHelper;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import org.datanucleus.PersistenceNucleusContext;
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.store.schema.SchemaAwareStoreManager;
import org.hsqldb.util.DatabaseManagerSwing;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Clob;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Prototyping",
        menuOrder = "999",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY
)
public class HsqlDbManagerMenu {


    private String url;

    @PostConstruct
    public void init(Map<String,String> properties) {
        this.url = properties.get("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionURL");
    }


    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            named = "HSQL DB Manager",
            cssClassFa = "database"
    )
    public void hsqlDbManager() {
        String[] args = {"--url", url, "--noexit" };
        DatabaseManagerSwing.main(args);
    }
    public boolean hideHsqlDbManager() {
        return Strings.isNullOrEmpty(url) || !url.contains("hsqldb:mem");
    }




    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            named = "Export DDL",
            cssClassFa = "database"
    )
    public Clob exportDdl(
            @ParameterLayout(named = "File name")
            final String fileName) throws IOException {

        final Map<String, String> datanucleusProps = Maps.newHashMap();

        datanucleusProps.put("javax.jdo.option.ConnectionURL", "jdbc:hsqldb:mem:" + UUID.randomUUID().toString());
        datanucleusProps.put("javax.jdo.option.ConnectionDriverName", "org.hsqldb.jdbcDriver");
        datanucleusProps.put("javax.jdo.option.ConnectionUserName", "sa");
        datanucleusProps.put("javax.jdo.option.ConnectionPassword", "");
        datanucleusProps.put("datanucleus.schema.autoCreateAll", "true");
        datanucleusProps.put("datanucleus.schema.validateAll", "false");
        datanucleusProps.put("datanucleus.identifier.case", "MixedCase");

        JDOPersistenceManagerFactory pmf = (JDOPersistenceManagerFactory) JDOHelper
                .getPersistenceManagerFactory(datanucleusProps);
        PersistenceNucleusContext ctx = pmf.getNucleusContext();

        final Set<Class<?>> persistenceCapableTypes = AppManifest.Registry.instance().getPersistenceCapableTypes();
        final Set<String> classNames = persistenceCapableTypes.stream().map(x -> x.getName())
                .collect(Collectors.toSet());

        Properties props = new Properties();

        final File tempFile = File.createTempFile("schemaTool", "ddl");
        tempFile.deleteOnExit();

        props.setProperty("ddlFilename", tempFile.getAbsolutePath());
        props.setProperty("completeDdl", "true");

        final SchemaAwareStoreManager storeManager = (SchemaAwareStoreManager) ctx.getStoreManager();
        storeManager.createSchemaForClasses(classNames, props);

        final String fileContents = Files.toString(tempFile, Charset.defaultCharset());

        return new Clob(fileName, "text/plain", fileContents);
    }

    public String default0ExportDdl() { return "schemaTool.ddl"; }



}
