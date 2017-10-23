package org.incode.module.mailchimp.dom.impl;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.mailchimp.dom.api.IMailChimpParty;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = MailChimpMember.class
)
public class MailChimpMemberRepository {

    @Programmatic
    public java.util.List<MailChimpMember> listAll() {
        return repositoryService.allInstances(MailChimpMember.class);
    }

    public MailChimpMember findByParty(final IMailChimpParty party) {
        return repositoryService.firstMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpMember.class,
                        "findByParty",
                        "party", party));
    }

    public MailChimpMember findByEmail(final String email) {
        return repositoryService.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpMember.class,
                        "findByEmail",
                        "email", email));
    }

    @Programmatic
    public MailChimpMember create(
            final String memberId,
            final String firstName,
            final String lastName,
            final String email) {
        final MailChimpMember mailChimpMember = new MailChimpMember();
        serviceRegistry2.injectServicesInto(mailChimpMember);
        mailChimpMember.setMemberId(memberId);
        mailChimpMember.setFirstName(firstName);
        mailChimpMember.setLastName(lastName);
        mailChimpMember.setEmailAddress(email);
        repositoryService.persistAndFlush(mailChimpMember);
        return mailChimpMember;
    }

    @Programmatic
    public MailChimpMember findOrCreateLocal(
            final IMailChimpParty party,
            final MailChimpList list
    ) {

        // guards
        if (party.excludeFromLists()!=null && party.excludeFromLists()) return null;
        if (party.getEmailAddress()==null || party.getEmailAddress().equals("")) return null;

        MailChimpMember mailChimpMember = findByEmail(party.getEmailAddress());
        if (mailChimpMember == null) {
            mailChimpMember = create(
                    null,
                    party.getFirstName(),
                    party.getLastName(),
                    party.getEmailAddress());
        }
        mailChimpListMemberLinkRepository.findOrCreateLocal(list, mailChimpMember);
        return mailChimpMember;
    }

    @Programmatic
    public MailChimpMember findOrCreateFromRemote(
            final String memberId,
            final MailChimpList list,
            final String firstName,
            final String lastName,
            final String email,
            final String status
    ) {
        MailChimpMember mailChimpMember = findByEmail(email);
        if (mailChimpMember == null) {
            mailChimpMember = create(memberId, firstName, lastName, email);
        } else {
            mailChimpMember.setMemberId(memberId);
        }
        mailChimpListMemberLinkRepository.findOrCreateFromRemote(list, mailChimpMember, status);
        return mailChimpMember;
    }

    @Inject MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    @Inject RepositoryService repositoryService;

    @Inject ServiceRegistry2 serviceRegistry2;

}
