package org.incode.module.mailchimp.dom.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = MailChimpList.class
)
public class MailChimpListRepository {

    public List<MailChimpList> autoComplete(@MinLength(3) final String search){
        return findByNameContains(search);
    }

    @Programmatic
    public List<MailChimpList> listAll() {
        return repositoryService.allInstances(MailChimpList.class);
    }

    @Programmatic
    public MailChimpList findByListId(
            final String listId
    ) {
        return repositoryService.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpList.class,
                        "findByListId",
                        "listId", listId));
    }

    @Programmatic
    public MailChimpList findByName(final String name) {
        return repositoryService.firstMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpList.class,
                        "findByName",
                        "name", name));
    }

    @Programmatic
    public List<MailChimpList> findByNameContains(final String searchString) {
        return repositoryService.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        MailChimpList.class,
                        "findByNameContains",
                        "searchString", searchString));
    }

    @Programmatic
    public MailChimpList create(final String listId, final String name, final String defaultMailSubject, final boolean newLocal) {
        final MailChimpList mailChimpList = new MailChimpList();
        serviceRegistry2.injectServicesInto(mailChimpList);
        mailChimpList.setListId(listId);
        mailChimpList.setName(name);
        mailChimpList.setDefaultMailSubject(defaultMailSubject);
        mailChimpList.setNewLocal(newLocal);
        repositoryService.persist(mailChimpList);
        return mailChimpList;
    }

    @Programmatic
    public MailChimpList findOrCreate(
            final String listId,
            final String name
    ) {
        MailChimpList mailChimpList = findByListId(listId);
        if (mailChimpList == null) {
            mailChimpList = create(listId, name, "STILL TODO SUBJECT", false);
        }
        return mailChimpList;
    }

    @Programmatic
    public void remove(final MailChimpList list){
        repositoryService.removeAndFlush(list);
    }

    @Inject RepositoryService repositoryService;

    @Inject ServiceRegistry2 serviceRegistry2;

}
