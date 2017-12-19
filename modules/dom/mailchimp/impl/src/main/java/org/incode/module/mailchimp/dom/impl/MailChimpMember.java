package org.incode.module.mailchimp.dom.impl;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "incodeMailchimp"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({

        @Query(
                name = "findByEmail", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.mailchimp.dom.impl.MailChimpMember "
                        + "WHERE emailAddress == :email "),
        @Query(
                name = "findByParty", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.mailchimp.dom.impl.MailChimpMember "
                        + "WHERE party == :party ")

})
@Unique(name = "MailChimpMember_emailAddress_UNQ", members = { "emailAddress" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class MailChimpMember implements Comparable<MailChimpMember> {

    public String title(){
        return getEmailAddress() + " - " + getLastName();
    }

    @Getter @Setter
    @Column(allowsNull = "false")
    private String emailAddress;

    @Getter @Setter
    @Column(allowsNull = "true")
    private String firstName;

    @Getter @Setter
    @Column(allowsNull = "false")
    private String lastName;

    @Getter @Setter
    @Column(allowsNull = "true")
    private String memberId;

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<MailChimpListMemberLink> getListsSubscribedTo(){
        return mailChimpListMemberLinkRepository.findByMember(this);
    }

    public static class MailChimpMemberDto {

        public MailChimpMemberDto(){
            this.merge_fields = new Merge_fields();
        }

        @Getter @Setter
        private String id;

        @Getter @Setter
        private String email_address;

        @Getter @Setter
        private String status;

        @Getter @Setter
        private Merge_fields merge_fields;

        class Merge_fields {

            @Getter @Setter
            private String FNAME;

            @Getter @Setter
            private String LNAME;

        }

    }

    @Programmatic
    public void remove(){
        for (MailChimpListMemberLink link : mailChimpListMemberLinkRepository.findByMember(this)){
            link.remove();
        }
        repositoryService.removeAndFlush(this);
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final MailChimpMember other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "memberId");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "memberId");
    }
    //endregion

    @Inject RepositoryService repositoryService;

    @Inject MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

}
