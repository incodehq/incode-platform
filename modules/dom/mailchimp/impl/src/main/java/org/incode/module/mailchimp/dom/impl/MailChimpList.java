package org.incode.module.mailchimp.dom.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;

import org.incode.module.mailchimp.dom.api.MailChimpService;

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
                name = "findByListId", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.mailchimpintegration.module.impl.MailChimpList "
                        + "WHERE listId == :listId "),
        @Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.mailchimpintegration.module.impl.MailChimpList "
                        + "WHERE name == :name "),
        @Query(
                name = "findByNameContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.mailchimpintegration.module.impl.MailChimpList "
                        + "WHERE name.toLowerCase().indexOf(:searchString.toLowerCase()) >= 0 ")
})
@Unique(name = "MailChimpList_listId_UNQ", members = { "listId" })
@DomainObject(
        editing = Editing.DISABLED,
        autoCompleteRepository = MailChimpListRepository.class
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class MailChimpList {

    public String title(){
        return getName();
    }

    @Getter @Setter
    @Column(allowsNull = "false")
    private String listId;

    @Getter @Setter
    @Column(allowsNull = "false")
    private String name;

    @Getter @Setter
    @Column(allowsNull = "false")
    @Property(hidden = Where.EVERYWHERE)
    private String defaultMailSubject;

    @Getter @Setter
    @Column(allowsNull = "true")
    private Boolean markedForDeletion;

    @Getter @Setter
    @Column(allowsNull = "true")
    private Boolean isDeletedRemote;

    @Getter @Setter
    @Column(allowsNull = "true")
    private Boolean newLocal;

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<MailChimpMember> getMembers(){
        return mailChimpListMemberLinkRepository.findByList(this).stream().map(x->x.getMember()).collect(Collectors.toList());
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public MailChimpList syncWithMailChimp(){
        mailChimpServiceImplementation.getMembers(this);
        return this;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete(){
        mailChimpService.deleteList(this);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void forceLocalDelete(){
        mailChimpService.forceLocalDelete(this);
    }

    @Programmatic
    public void removeLocal(){
        removeAllMemberLinks();
        mailChimpListRepository.remove(this);
    }

    private void removeAllMemberLinks() {
        for (MailChimpListMemberLink link : mailChimpListMemberLinkRepository.findByList(this)){
            link.remove();
        }
    }


    public static class MailListMemberDto {

        @Getter @Setter
        private String id;

        @Getter @Setter
        private String name;

        @Getter @Setter
        private List<MailChimpMember.MailChimpMemberDto> members;

    }

    @Inject MailChimpServiceImplementation mailChimpServiceImplementation;

    @Inject MailChimpMemberRepository mailChimpMemberRepository;

    @Inject MessageService messageService;

    @Inject MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    @Inject MailChimpListRepository mailChimpListRepository;

    @Inject MailChimpService mailChimpService;

}
