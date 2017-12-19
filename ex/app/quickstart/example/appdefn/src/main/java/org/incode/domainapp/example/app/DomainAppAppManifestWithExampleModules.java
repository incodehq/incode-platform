package org.incode.domainapp.example.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.excel.ExcelModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;
import org.isisaddons.module.poly.PolyModule;
import org.isisaddons.module.servletapi.ServletApiModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.tags.TagsModule;
import org.isisaddons.module.xdocreport.dom.XDocReportModule;

import org.incode.domainapp.example.dom.ExampleDomSubmodule;
import org.incode.domainapp.example.dom.demo.ExampleDomDemoDomSubmodule;
import org.incode.module.alias.dom.AliasModule;
import org.incode.module.classification.dom.ClassificationModule;
import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.communications.dom.CommunicationsModuleDomModule;
import org.incode.module.country.dom.CountryModule;
import org.incode.module.docfragment.dom.DocFragmentModuleDomModule;
import org.incode.module.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.module.docrendering.stringinterpolator.dom.StringInterpolatorDocRenderingModule;
import org.incode.module.docrendering.xdocreport.dom.XDocReportDocRenderingModule;
import org.incode.module.document.dom.DocumentModule;
import org.incode.module.mailchimp.dom.MailchimpModule;
import org.incode.module.note.dom.NoteModule;

import domainapp.appdefn.DomainAppAppManifest;

public class DomainAppAppManifestWithExampleModules extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withAdditionalModules(
                    ExampleAppSubmodule.class,
                    ExampleDomSubmodule.class,
                    ExampleDomDemoDomSubmodule.class,

                    // lib
                    FreemarkerDocRenderingModule.class,
                    StringInterpolatorDocRenderingModule.class,
                    XDocReportDocRenderingModule.class,
                    DocxModule.class,
                    ExcelModule.class,
                    FakeDataModule.class,
                    FreeMarkerModule.class,
                    PolyModule.class,
                    PdfBoxModule.class,
                    ServletApiModule.class,
                    StringInterpolatorModule.class,
                    XDocReportModule.class,

                    // subdomains (dom)
                    AliasModule.class,
                    ClassificationModule.class,
                    CommChannelModule.class,
                    CommunicationsModuleDomModule.class,
                    CountryModule.class,
                    DocFragmentModuleDomModule.class,
                    DocumentModule.class,
                    NoteModule.class,
                    TagsModule.class,
                    MailchimpModule.class

            )
            // required by Maichimp Module
            .withConfigurationProperty("isis.service.mailchimp.base-url","https://us6.api.mailchimp.com/3.0")
            .withConfigurationProperty("isis.service.mailchimp.secret","e22a20f29430c3df0d2b7840af9921d6-us6")
            .withConfigurationProperty("isis.service.mailchimp.connect-timeout","2000")
            .withConfigurationProperty("isis.service.mailchimp.read-timeout","30000")
            .withConfigurationProperty("isis.service.mailchimp.company","MyCompany")
            .withConfigurationProperty("isis.service.mailchimp.address1","My Street 1")
            .withConfigurationProperty("isis.service.mailchimp.city","MyCity")
            .withConfigurationProperty("isis.service.mailchimp.state","MyState")
            .withConfigurationProperty("isis.service.mailchimp.zip","MyZip")
            .withConfigurationProperty("isis.service.mailchimp.country","MyCountry")
            .withConfigurationProperty("isis.service.mailchimp.from_name","MyCompany - mySlogan")
            .withConfigurationProperty("isis.service.mailchimp.from_email","my@email.com")
            .withConfigurationProperty("isis.service.mailchimp.language","en")
            .withConfigurationProperty("isis.service.mailchimp.permissionreminder","You are receiving this mail because .....")
            ;

    public DomainAppAppManifestWithExampleModules() {
        super(BUILDER);
    }

}
