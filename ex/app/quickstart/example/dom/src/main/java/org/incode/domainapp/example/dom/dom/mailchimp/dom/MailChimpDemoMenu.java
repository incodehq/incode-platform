package org.incode.domainapp.example.dom.dom.mailchimp.dom;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;

import org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty.DemoParty;
import org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty.DemoPartyRepository;
import org.incode.module.mailchimp.dom.api.IMailChimpParty;
import org.incode.module.mailchimp.dom.api.MailChimpService;
import org.incode.module.mailchimp.dom.impl.MailChimpList;
import org.incode.module.mailchimp.dom.impl.MailChimpListRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;
import org.incode.module.mailchimp.dom.impl.MailChimpMemberRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "org.incode.domainapp.example.dom.dom.mailchimp.dom.MailChimpDemoMenu"
)
public class MailChimpDemoMenu {

    public DemoParty findOrCreateParty(
            @ParameterLayout(named = "name")
            final String name,
            @ParameterLayout(named = "email")
            final String email,
            @ParameterLayout(named = "send email")
            final boolean sendMail){
        return demoPartyRepository.findOrCreate(name, email, sendMail);
    }

    public List<MailChimpList> syncMailChimpLists(){
        return mailChimpService.syncMailChimpLists();
    }

    public MailChimpList syncMembers(final MailChimpList list){
        return mailChimpService.syncMembers(list);
    }

    /**
     * For demo purpose only; use mailChimpService#createList for normal production
     * @param name
     * @param defaultSubject
     * @return
     */
    public MailChimpList createListLocalForDemo(
            @ParameterLayout(named = "name")
            final String name,
            @ParameterLayout(named = "default subject")
            final String defaultSubject){
        return mailChimpListRepository.create(UUID.randomUUID().toString(), name, defaultSubject, true);
    }

    /**
     * For demo purpose only; use mailChimpService#setMembersToCorrespondWithPartyList for normal production
     * @param list
     * @return
     */
    public MailChimpList addDemoPartiesToListLocalForDemo(final MailChimpList list){
        List<IMailChimpParty> parties = demoPartyRepository.listAll().stream().map(IMailChimpParty.class::cast).collect(Collectors.toList());
        return mailChimpService.setMembersToCorrespondWithPartyListNoSync(parties, list);
    }

    public List<MailChimpList> allMailChimpLists(){
        return mailChimpService.getAllLists();
    }

    public MailChimpList createListAndSync(
            @ParameterLayout(named = "name")
            final String name,
            @ParameterLayout(named = "default subject")
            final String defaultSubject){
        return mailChimpService.createList(name, defaultSubject);
    }

    public List<MailChimpList> removeListAndSync(final MailChimpList list){
        mailChimpService.deleteList(list);
        return mailChimpService.getAllLists();
    }

    public MailChimpList addDemoPartiesToListAndSync(final MailChimpList list){
        List<IMailChimpParty> parties = demoPartyRepository.listAll().stream().map(IMailChimpParty.class::cast).collect(Collectors.toList());
        return mailChimpService.setMembersToCorrespondWithPartyList(parties, list);
    }

    public List<MailChimpMember> allMailChimpMembers(){
        return mailChimpMemberRepository.listAll();
    }

    public  List<MailChimpMember> membersWithoutList(){
        return mailChimpService.findOrphanedMembers();
    }

    public void removeMembersWithoutList(){
        mailChimpService.removeAllOrphanedMembers();
    }


    @Inject
    DemoPartyRepository demoPartyRepository;

    @Inject
    MailChimpService mailChimpService;

    @Inject
    MailChimpMemberRepository mailChimpMemberRepository;

    @Inject
    MailChimpListRepository mailChimpListRepository;
}
