package org.incode.module.mailchimp.dom.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = MailChimpListMemberLink.class
)
public class MailChimpListMemberLinkRepository {

    @Programmatic
    public List<MailChimpListMemberLink> listAll() {
        return repositoryService.allInstances(MailChimpListMemberLink.class);
    }

    private MailChimpListMemberLink create(final MailChimpList list, final MailChimpMember member, final boolean newLocal, final String status) {
        final MailChimpListMemberLink mailChimpListMemberLink = new MailChimpListMemberLink();
        serviceRegistry2.injectServicesInto(mailChimpListMemberLink);
        mailChimpListMemberLink.setList(list);
        mailChimpListMemberLink.setMember(member);
        mailChimpListMemberLink.setNewLocal(newLocal);
        mailChimpListMemberLink.setStatus(status);
        repositoryService.persist(mailChimpListMemberLink);
        return mailChimpListMemberLink;
    }

    @Programmatic
    public MailChimpListMemberLink findOrCreateFromRemote(
            final MailChimpList list,
            final MailChimpMember member,
            final String status
    ) {
        MailChimpListMemberLink mailChimpListMemberLink = findUnique(list, member);
        if (mailChimpListMemberLink == null) {
            mailChimpListMemberLink = create(list, member, false, status);
        }
        mailChimpListMemberLink.setStatus(status);
        return mailChimpListMemberLink;
    }

    @Programmatic
    public MailChimpListMemberLink findOrCreateLocal(
            final MailChimpList list,
            final MailChimpMember member
    ) {
        MailChimpListMemberLink mailChimpListMemberLink = findUnique(list, member);
        if (mailChimpListMemberLink == null) {
            mailChimpListMemberLink = create(list, member, true, "subscribed");
        }
        return mailChimpListMemberLink;
    }

    @Programmatic
    public MailChimpListMemberLink findUnique(
            final MailChimpList list,
            final MailChimpMember member
    ) {
        return repositoryService.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpListMemberLink.class,
                        "findUnique",
                        "list", list, "member", member));
    }

    @Programmatic
    public List<MailChimpListMemberLink> findByMember(
            final MailChimpMember member
    ) {
        return repositoryService.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpListMemberLink.class,
                        "findByMember",
                        "member", member));
    }

    @Programmatic
    public List<MailChimpListMemberLink> findByList(
            final MailChimpList list
    ) {
        return repositoryService.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpListMemberLink.class,
                        "findByList",
                        "list", list));
    }

    @Programmatic
    public void remove(final MailChimpList mailChimpList, final MailChimpMember member) {
        MailChimpListMemberLink link = findUnique(mailChimpList, member);
        repositoryService.removeAndFlush(link);
    }

    @Programmatic
    public void remove(final MailChimpListMemberLink mailChimpListMemberLink) {
        repositoryService.removeAndFlush(mailChimpListMemberLink);
    }

    @Inject RepositoryService repositoryService;

    @Inject ServiceRegistry2 serviceRegistry2;


}
