package org.incode.domainapp.example.dom.dom.tags.dom.demo;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import org.isisaddons.module.tags.dom.Tag;
import org.isisaddons.module.tags.dom.Tags;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a general purpose mechanism for tagging (or labelling) any entity with a named
 * (string) value.
 */
@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema = "exampleDomTags"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy= IdGeneratorStrategy.NATIVE,
        column="id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "Tag_name_UNQ", members = { "name" })
})
@MemberGroupLayout(columnSpans = {4,4,4,12},
        left = {"General"},
        middle = {"Tagged"},
        right = {"Value"}
)
public class DemoTaggableObject {


    private static final String TAG_NAME_BRAND = "Brand";
    private static final String TAG_NAME_SECTOR = "Sector";


    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    @Getter @Setter
    private String name;


    @javax.jdo.annotations.Column(name = "BRANDTAG_ID", allowsNull="true")
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private Tag brandTag;


    @javax.jdo.annotations.Column(name = "SECTORTAG_ID", allowsNull="true")
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private Tag sectorTag;



    @javax.jdo.annotations.NotPersistent
    @MemberOrder(sequence="2")
    public String getBrand() {
        final Tag existingTag = getBrandTag();
        return existingTag != null ? existingTag.getValue() : null;
    }

    public void setBrand(final String brand) {
        final Tag existingTag = getBrandTag();
        Tag tag = tags.tagFor(this, existingTag, TAG_NAME_BRAND, brand);
        setBrandTag(tag);
    }

    public List<String> choicesBrand() {
        return tags.choices(this, TAG_NAME_BRAND);
    }






    @MemberOrder(name="brand", sequence = "2")
    public DemoTaggableObject updateBrand(
            @ParameterLayout(named="Tag") @Parameter(optionality = Optionality.OPTIONAL)
            final String brand) {
        setBrand(brand);
        return this;
    }

    public String default0UpdateBrand() {
        return getBrand();
    }

    public List<String> choices0UpdateBrand() {
        return tags.choices(this, TAG_NAME_BRAND);
    }





    @MemberOrder(name="brand", sequence = "1")
    public DemoTaggableObject newBrand(
            @ParameterLayout(named="Tag") @Parameter(optionality = Optionality.OPTIONAL)
            final String brand) {
        setBrand(brand);
        return this;
    }

    public String default0NewBrand() {
        return getBrand();
    }




    @javax.jdo.annotations.NotPersistent
    @MemberOrder(sequence="2")
    public String getSector() {
        final Tag existingTag = getSectorTag();
        return existingTag != null ? existingTag.getValue() : null;
    }

    public void setSector(final String sector) {
        final Tag existingTag = getSectorTag();
        Tag tag = tags.tagFor(this, existingTag, TAG_NAME_SECTOR, sector);
        setSectorTag(tag);
    }

    public List<String> choicesSector() {
        return tags.choices(this, TAG_NAME_SECTOR);
    }



    @MemberOrder(name="sector", sequence = "2")
    public DemoTaggableObject updateSector(
            @ParameterLayout(named="Tag") @Parameter(optionality = Optionality.OPTIONAL)
            final String sector) {
        setSector(sector);
        return this;
    }

    public String default0UpdateSector() {
        return getSector();
    }

    public List<String> choices0UpdateSector() {
        return tags.choices(this, TAG_NAME_SECTOR);
    }



    @MemberOrder(name="sector", sequence = "1")
    public DemoTaggableObject newSector(
            @ParameterLayout(named="Tag") @Parameter(optionality = Optionality.OPTIONAL)
            final String sector) {
        setSector(sector);
        return this;
    }

    public String default0NewSector() {
        return getSector();
    }


    // //////////////////////////////////////

    @javax.inject.Inject
    private Tags tags;

}
