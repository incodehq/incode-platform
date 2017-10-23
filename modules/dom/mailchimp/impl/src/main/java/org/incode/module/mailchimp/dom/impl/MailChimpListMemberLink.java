package org.incode.module.mailchimp.dom.impl;

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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "mailchimp"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({
        @Query(
                name = "findUnique", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.mailchimpintegration.module.impl.MailChimpListMemberLink "
                        + "WHERE list == :list && member == :member "),
        @Query(
                name = "findByList", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.mailchimpintegration.module.impl.MailChimpListMemberLink "
                        + "WHERE list == :list "),
        @Query(
                name = "findByMember", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.mailchimpintegration.module.impl.MailChimpListMemberLink "
                        + "WHERE member == :member ")
})
@Unique(name = "MailChimpListMemberLink_list_member_UNQ", members = { "list", "member" })
@DomainObject(
        editing = Editing.DISABLED
)
public class MailChimpListMemberLink {

    public MailChimpListMemberLink(){}

    public MailChimpListMemberLink(final MailChimpList list, final MailChimpMember member){
        this.list = list;
        this.member = member;
    }

    @Getter @Setter
    @Column(allowsNull = "false")
    private MailChimpList list;

    @Getter @Setter
    @Column(allowsNull = "false")
    private MailChimpMember member;

    @Getter @Setter
    @Column(allowsNull = "false")
    private String status;

    @Getter @Setter
    @Column(allowsNull = "true")
    private Boolean markedForDeletion;

    @Getter @Setter
    @Column(allowsNull = "true")
    private Boolean isDeletedRemote;

    @Getter @Setter
    @Column(allowsNull = "true")
    private Boolean newLocal;

    @Programmatic
    public void remove() {
        mailChimpListMemberLinkRepository.remove(this);
    }

    @Programmatic
    public void deleteLocal() {
        setMarkedForDeletion(true);
    }

    @Inject MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

}
