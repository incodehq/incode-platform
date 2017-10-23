package org.incode.module.mailchimp.dom.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry;

import org.incode.module.mailchimp.dom.impl.MailChimpList;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLink;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLinkRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpListRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;
import org.incode.module.mailchimp.dom.impl.MailChimpMemberRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpServiceImplementation;

@DomainService(nature = NatureOfService.DOMAIN)
public class MailChimpService {

    /**
     * Synchronizes local and remote lists (lists only, not the members)
     * @return all local lists
     */
    public List<MailChimpList> syncMailChimpLists() {

        // delete remote lists
        List<MailChimpList> listsToDeleteOnRemote = mailChimpListRepository.listAll()
                .stream()
                .filter(x->x.getMarkedForDeletion()!=null && x.getMarkedForDeletion())
                .collect(Collectors.toList());
        for (MailChimpList list : listsToDeleteOnRemote){
            try {
                mailChimpServiceImplementation.deleteList(list);
            } catch (Exception e){
                // nothing
            }
        }

        // delete local lists that are deleted remote
        List<MailChimpList> listsDeletedRemote = mailChimpListRepository.listAll()
                .stream()
                .filter(x->x.getIsDeletedRemote()!=null && x.getIsDeletedRemote())
                .collect(Collectors.toList());
        for (MailChimpList list : listsDeletedRemote){
            list.removeLocal();
        }

        // add new local lists on remote
        List<MailChimpList> listsNewLocal = mailChimpListRepository.listAll()
                .stream()
                .filter(x->x.getNewLocal()!=null && x.getNewLocal())
                .collect(Collectors.toList());
        for (MailChimpList list : listsNewLocal) {
            try {
                String listId = mailChimpServiceImplementation.createList(list.getName(), list.getDefaultMailSubject());
                list.setListId(listId);
                list.setNewLocal(false);
            }
            catch (Exception e){
                // nothing
            }
        }

        mailChimpServiceImplementation.getAllListsFromRemote();

        removeListsNotOnRemote();

        return getAllLists();
    }

    void removeListsNotOnRemote(){
        List<MailChimpList> listsOnRemote = mailChimpServiceImplementation.getListsFromRemoteForComparison();
        for (MailChimpList localList : getAllLists()){
            if (localList.getNewLocal()==null || !localList.getNewLocal()) {
                boolean found = false;
                for (MailChimpList remoteList : listsOnRemote) {
                    if (localList.getListId().equals(remoteList.getListId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    mailChimpListRepository.remove(localList);
                }
            }
        }
    }

    /**
     * Synchronizes the local list and the remote list
     * @param mailChimpList
     * @return the local list
     */
    public MailChimpList syncMembers(final MailChimpList mailChimpList) {

        // delete remote members
        List<MailChimpMember> membersToDeleteOnRemote =
                mailChimpListMemberLinkRepository.findByList(mailChimpList)
                .stream()
                .filter(x->x.getMarkedForDeletion()!=null && x.getMarkedForDeletion()==true)
                .map(x->x.getMember())
                .collect(Collectors.toList());

        for (MailChimpMember member : membersToDeleteOnRemote){
            try {
                mailChimpServiceImplementation.deleteMember(member, mailChimpList);
            }
            catch (Exception e){
                // nothing
            }
        }

        // delete links of members that are deleted remote
        List<MailChimpListMemberLink> memberLinksOfMembersDeletedRemote =
                mailChimpListMemberLinkRepository.findByList(mailChimpList)
                        .stream()
                        .filter(x->x.getMarkedForDeletion()!=null && x.getMarkedForDeletion())
                        .collect(Collectors.toList());

        for (MailChimpListMemberLink link : memberLinksOfMembersDeletedRemote){
            link.remove();
        }

        // add new local members on remote
        List<MailChimpListMemberLink> linksOfNewMembers =
                mailChimpListMemberLinkRepository.findByList(mailChimpList)
                        .stream()
                        .filter(x->x.getNewLocal()!=null && x.getNewLocal())
                        .collect(Collectors.toList());
        for (MailChimpListMemberLink link : linksOfNewMembers){
            mailChimpServiceImplementation.createMember(link.getMember(), mailChimpList);
        }

        // get members from remote
        mailChimpServiceImplementation.getMembers(mailChimpList);

        removeMemberLinksNotOnRemote(mailChimpList);

        return mailChimpList;
    }

    void removeMemberLinksNotOnRemote(final MailChimpList mailChimpList){
        List<MailChimpMember> remoteMembers = mailChimpServiceImplementation.getRemoteMembersForComparison(mailChimpList);
        for (MailChimpMember memberLocal : mailChimpList.getMembers()){
            boolean found = false;
            for (MailChimpMember memberRemote : remoteMembers){
                if (memberLocal.getMemberId()!=null && memberLocal.getMemberId().equals(memberRemote.getMemberId())){
                    found = true;
                    break;
                }
            }
            if (!found){
                MailChimpListMemberLink link = mailChimpListMemberLinkRepository.findUnique(mailChimpList, memberLocal);
                if (link.getNewLocal()){
                    if (doubleEmailAndNewLocalSet(mailChimpList, memberLocal)){
                        link.remove();
                    }
                } else {
                    link.remove();
                }
            }
        }
    }

    private boolean doubleEmailAndNewLocalSet(final MailChimpList list, final MailChimpMember memberLocal){
        List<MailChimpMember> membersInListWithSameEmail = list.getMembers()
                .stream()
                .filter(x->x.getEmailAddress().equals(memberLocal.getEmailAddress()))
                .collect(Collectors.toList());
        if (!membersInListWithSameEmail.isEmpty() && mailChimpListMemberLinkRepository.findUnique(list, memberLocal).getNewLocal()){
            return true;
        }
        return false;
    }

    /**
     * Removes the member from all lists
     * @param mailChimpMember
     */
    public void removeMember(final MailChimpMember mailChimpMember) {
        // mark all links of member for deletion
        List<MailChimpListMemberLink> linksToMarkForDeletion = mailChimpListMemberLinkRepository.findByMember(mailChimpMember);
        linksToMarkForDeletion.stream()
                .forEach(x->x.setMarkedForDeletion(true));
        // sync list with remote
        for (MailChimpListMemberLink link : linksToMarkForDeletion){
            syncMembers(link.getList());
        }
    }

    /**
     * Removes the member that corresponds to party from all lists
     * @param party
     */
    public void removeMember(final IMailChimpParty party) {
        MailChimpMember memberToDelete = mailChimpMemberRepository.findByParty(party);
        removeMember(memberToDelete);
    }

    /**
     * Removes member from list
     * @param member
     * @param mailChimpList
     * @return
     */
    public MailChimpList removeFromList(final MailChimpMember member, final MailChimpList mailChimpList) {
        MailChimpListMemberLink link = mailChimpListMemberLinkRepository.findUnique(mailChimpList, member);
        if (link!=null){
            link.setMarkedForDeletion(true);
        }
        syncMembers(mailChimpList);
        return mailChimpList;
    }

    /**
     * Removes member that corresponds to party from list
     * @param party
     * @param mailChimpList
     * @return
     */
    public MailChimpList removeFromList(final IMailChimpParty party, final MailChimpList mailChimpList) {
        MailChimpMember member = mailChimpMemberRepository.findByParty(party);
        if (member!=null){
            removeFromList(member, mailChimpList);
        }
        return mailChimpList;
    }

    /**
     * Adds member that corresponds to party to list - if needed creates member
     * @param party
     * @param list
     * @return
     */
    public MailChimpMember addToList(final IMailChimpParty party, final MailChimpList list) {

        // guard
        if (party.excludeFromLists()!=null && party.excludeFromLists()) return null;

        MailChimpMember newMember = mailChimpMemberRepository.findOrCreateLocal(party, list);
        syncMembers(list);
        return newMember;
    }


    public MailChimpList createList(final String name, final String defaultMailSubject) {
        MailChimpList newLocalList = mailChimpListRepository.create("newList", name, defaultMailSubject, true);
        syncMailChimpLists();
        return newLocalList;
    }


    public void deleteList(final MailChimpList list) {
        list.setMarkedForDeletion(true);
        syncMailChimpLists();
    }

    public void forceLocalDelete(final MailChimpList list){
        mailChimpServiceImplementation.deleteListLocal(list);
    }

    /**
     * Harmonizes a list of IMailChimpParty and members of a list
     * @param partyList
     * @param mailChimpList
     * @return
     */
    public MailChimpList setMembersToCorrespondWithPartyList(final List<IMailChimpParty> partyList, final MailChimpList mailChimpList){

        //first check members to delete from list
        List<MailChimpMember> membersToRemainInlist = mapPartyListToMemberList(partyList);
        for (MailChimpMember memberInList : mailChimpList.getMembers()){
            boolean found = false;
            for (MailChimpMember mailChimpMember : membersToRemainInlist){
                if (mailChimpMember.getEmailAddress().equals(memberInList.getEmailAddress())){
                    found=true;
                    break;
                }
            }
            if (!found){
                mailChimpListMemberLinkRepository.findUnique(mailChimpList, memberInList).deleteLocal();
            }
        }

        // then add membes corresponding to parties if needed
        for (IMailChimpParty party : partyList){
            mailChimpMemberRepository.findOrCreateLocal(party, mailChimpList);
        }

        // then sync
        syncMembers(mailChimpList);

        return mailChimpList;
    }

    List<MailChimpMember> mapPartyListToMemberList(final List<IMailChimpParty> partyList){
        List<MailChimpMember> result = new ArrayList<>();
        for (IMailChimpParty party : partyList){
            MailChimpMember candidate = mapPartyToMember(party);
            if (candidate!=null && !result.contains(candidate)){
                result.add(candidate);
            }
        }
        return result;
    }

    MailChimpMember mapPartyToMember(final IMailChimpParty party){
        if (party.getEmailAddress()==null || (party.excludeFromLists()!=null && party.excludeFromLists())) return null;
        return mailChimpMemberRepository.findByEmail(party.getEmailAddress());
    }

    /**
     * returns first match by name
     * @param name
     * @return
     */
    public MailChimpList findByName(final String name) {
        return mailChimpListRepository.findByName(name);
    }

    public List<MailChimpList> findByNameContains(final String search){
        return mailChimpListRepository.findByNameContains(search);
    }

    public List<MailChimpList> getAllLists() {
        return mailChimpListRepository.listAll();
    }

    /**
     * Finds members not linked to any list
     * @return
     */
    public List<MailChimpMember> findOrphanedMembers(){
        return mailChimpMemberRepository.listAll()
                .stream()
                .filter(x->notInAList(x))
                .collect(Collectors.toList());
    }

    private boolean notInAList(final MailChimpMember member) {
        return mailChimpListMemberLinkRepository.findByMember(member).isEmpty();
    }

    /**
     * clean-up action that removes all orphaned members - this is - members not linked to any list
     */
    public void removeAllOrphanedMembers(){
        findOrphanedMembers().stream().forEach(x->x.remove());
    }


    @Inject MailChimpServiceImplementation mailChimpServiceImplementation;

    @Inject MailChimpListRepository mailChimpListRepository;

    @Inject MailChimpMemberRepository mailChimpMemberRepository;

    @Inject MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    @Inject ServiceRegistry serviceRegistry;

}
