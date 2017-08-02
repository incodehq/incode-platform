package org.incode.domainapp.example.dom.wkt.pdfjs.dom.demo;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema="isisaddonsPdfjsDemo")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT,
        cssClassUiEvent = DemoObject.CssClassUiEvent.class
)
public class DemoObject implements Comparable<DemoObject> {

    public static class CssClassUiEvent
            extends org.apache.isis.applib.services.eventbus.CssClassUiEvent<DemoObject> {}

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String name;

    @MemberOrder(name = "name", sequence = "1")
    public DemoObject updateName(String name) {
        setName(name);
        return this;
    }
    public String default0UpdateName() {
        return getName();
    }


    @Property
    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    private String url;
    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(name = "url", sequence = "1")
    public URL openUrl() throws MalformedURLException {
        return new java.net.URL(getUrl());
    }
    public String disableOpenUrl() {
        if (getUrl() == null)
            return "No URL to open";
        return null;
    }

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "blob_name"),
            @javax.jdo.annotations.Column(name = "blob_mimetype"),
            @javax.jdo.annotations.Column(name = "blob_bytes", jdbcType = "BLOB", sqlType = "LONGVARBINARY")
    })
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES
    )

    @Setter
    private Blob blob;

    //    @Getter(onMethod = @__({ }))  throwing a compile exception :-(
    @PdfJsViewer(initialPageNum = 1, initialScale = Scale._1_00, initialHeight = 600)
    public Blob getBlob() {
        return blob;
    }
    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "name");
    }

    @Override
    public int compareTo(final DemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

}
