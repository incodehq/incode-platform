package org.incode.module.mailchimp.dom.api;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import org.incode.module.mailchimp.dom.impl.MailChimpList;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLink;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLinkRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpListRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;
import org.incode.module.mailchimp.dom.impl.MailChimpMemberRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpServiceImplementation;
import org.incode.module.mailchimp.dom.impl.PartyForTest;

public class MailChimpServiceTest {

    @Rule public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock MailChimpMemberRepository mailChimpMemberRepository;


    @Test
    public void mapPartyListToMemberList_works() throws Exception {

        // given
        MailChimpService mailChimpService = new MailChimpService();
        mailChimpService.mailChimpMemberRepository = mailChimpMemberRepository;
        IMailChimpParty party1 = new PartyForTest("", "", "some@email.adr", false);
        IMailChimpParty party2 = new PartyForTest("", "", "some@email.adr", false);
        IMailChimpParty party3 = new PartyForTest("", "", "someOther@email.adr", false);
        MailChimpMember memberCorrespondingToParty1And2 = new MailChimpMember();
        MailChimpMember memberCorrespondingToParty3 = new MailChimpMember();
        List<IMailChimpParty> partyList = Arrays.asList(party1, party2, party3);

        // expect
        context.checking(new Expectations(){{
            oneOf(mailChimpMemberRepository).findByEmail(party1.getEmailAddress());
            will(returnValue(memberCorrespondingToParty1And2));
            oneOf(mailChimpMemberRepository).findByEmail(party2.getEmailAddress());
            will(returnValue(memberCorrespondingToParty1And2));
            oneOf(mailChimpMemberRepository).findByEmail(party3.getEmailAddress());
            will(returnValue(memberCorrespondingToParty3));

        }});

        // when
        final List<MailChimpMember> result = mailChimpService.mapPartyListToMemberList(partyList);
        // then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(memberCorrespondingToParty1And2);
        Assertions.assertThat(result).contains(memberCorrespondingToParty3);

    }

    @Test
    public void mapPartyToMember_works() throws Exception {

        // given
        MailChimpService mailChimpService = new MailChimpService();
        mailChimpService.mailChimpMemberRepository = mailChimpMemberRepository;
        PartyForTest party = new PartyForTest("", "", "some@email.adr", false);
        MailChimpMember member = new MailChimpMember();

        // expect
        context.checking(new Expectations(){{
            oneOf(mailChimpMemberRepository).findByEmail(party.getEmailAddress());
            will(returnValue(member));
        }});

        // when
        MailChimpMember result = mailChimpService.mapPartyToMember(party);
        // then
        Assertions.assertThat(result).isEqualTo(member);

        // expect nothing when excluded
        // and given
        party.setExcludeFromLists(true);
        // when then
        Assertions.assertThat(mailChimpService.mapPartyToMember(party)).isNull();

        // expect nothing when no email
        // and given
        party.setExcludeFromLists(false);
        party.setEmailAddress(null);
        // when then
        Assertions.assertThat(mailChimpService.mapPartyToMember(party)).isNull();
    }


    @Mock MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    @Test
    public void setMembersToCorrespondWithPartyList_works() throws Exception {

        // given
        MailChimpMember existingMember = new MailChimpMember();
        existingMember.setEmailAddress("some@adre.ss");
        MailChimpMember newMember = new MailChimpMember();
        newMember.setEmailAddress("some@email.adr");
        MailChimpList list = new MailChimpList(){
            @Override
            public List<MailChimpMember> getMembers(){
                return Arrays.asList(existingMember);
            }
        };
        MailChimpService mailChimpService = new MailChimpService(){
            @Override
            public MailChimpList syncMembers(final MailChimpList mailChimpList) {
                return null;
            }
            @Override
            List<MailChimpMember> mapPartyListToMemberList(final List<IMailChimpParty> partyList){
                return Arrays.asList(newMember);
            }
        };
        mailChimpService.mailChimpMemberRepository = mailChimpMemberRepository;
        mailChimpService.mailChimpListMemberLinkRepository = mailChimpListMemberLinkRepository;

        IMailChimpParty party1 = new PartyForTest("", "", "some@email.adr", false);
        List<IMailChimpParty> partyList = Arrays.asList(party1);

        // expect
        context.checking(new Expectations(){{
            oneOf(mailChimpListMemberLinkRepository).findUnique(list, existingMember).deleteLocal();
            oneOf(mailChimpMemberRepository).findOrCreateLocal(party1,list);
        }});

        // when
        mailChimpService.setMembersToCorrespondWithPartyList(partyList, list);

    }

    @Mock MailChimpServiceImplementation mailChimpServiceImplementation;

    @Test
    public void removeMembersNotOnRemote_works() throws Exception {

        // given
        MailChimpService mailChimpService = new MailChimpService();
        mailChimpService.mailChimpServiceImplementation = mailChimpServiceImplementation;
        mailChimpService.mailChimpListMemberLinkRepository = mailChimpListMemberLinkRepository;
        MailChimpMember localMemberNotOnRemote = new MailChimpMember();
        localMemberNotOnRemote.setMemberId("not_on_remote");;
        MailChimpList list = new MailChimpList(){
            @Override
            public List<MailChimpMember> getMembers(){
                return Arrays.asList(localMemberNotOnRemote);
            }
        };
        MailChimpListMemberLink linkToRemove = new MailChimpListMemberLink();

        // expect
        context.checking(new Expectations(){{
            oneOf(mailChimpServiceImplementation).getRemoteMembersForComparison(list);
            will(returnValue(Arrays.asList())); // empty list, so no members found on remote
            oneOf(mailChimpListMemberLinkRepository).findUnique(list, localMemberNotOnRemote).remove();
        }});

        // when
        mailChimpService.removeMemberLinksNotOnRemote(list);

    }

    @Mock MailChimpListRepository mailChimpListRepository;

    @Test
    public void removeListsNotOnRemote_works() throws Exception {

        // given
        MailChimpList LocalListNotOnRemote = new MailChimpList();
        MailChimpService mailChimpService = new MailChimpService(){
            @Override
            public List<MailChimpList> getAllLists() {
                return Arrays.asList(LocalListNotOnRemote);
            }
        };
        mailChimpService.mailChimpServiceImplementation = mailChimpServiceImplementation;
        mailChimpService.mailChimpListRepository = mailChimpListRepository;

        // expect
        context.checking(new Expectations(){{
            oneOf(mailChimpServiceImplementation).getListsFromRemoteForComparison();
            will(returnValue(Arrays.asList())); // empty, so no lists found on remote
            oneOf(mailChimpListRepository).remove(LocalListNotOnRemote);

        }});

        // when
        mailChimpService.removeListsNotOnRemote();

    }

    @Test
    public void removeListsNotOnRemote_works_when_local_list_is_new() throws Exception {

        // given
        MailChimpList LocalListNotOnRemote = new MailChimpList();
        LocalListNotOnRemote.setNewLocal(true);
        MailChimpService mailChimpService = new MailChimpService(){
            @Override
            public List<MailChimpList> getAllLists() {
                return Arrays.asList(LocalListNotOnRemote);
            }
        };
        mailChimpService.mailChimpServiceImplementation = mailChimpServiceImplementation;

        // expect no call mailChimpListRepository.remove(LocalListNotOnRemote)
        context.checking(new Expectations(){{
            oneOf(mailChimpServiceImplementation).getListsFromRemoteForComparison();
            will(returnValue(Arrays.asList())); // empty, so no lists found on remote
        }});

        // when
        mailChimpService.removeListsNotOnRemote();

    }

    @Test
    public void findOrphanedMembers_works() throws Exception {

        // given
        MailChimpService mailChimpService = new MailChimpService();
        mailChimpService.mailChimpMemberRepository = mailChimpMemberRepository;
        mailChimpService.mailChimpListMemberLinkRepository = mailChimpListMemberLinkRepository;
        MailChimpMember orphan = new MailChimpMember();
        MailChimpMember noOrphan = new MailChimpMember();

        // expect
        context.checking(new Expectations(){{
            oneOf(mailChimpMemberRepository).listAll();
            will(returnValue(Arrays.asList(orphan, noOrphan)));

            oneOf(mailChimpListMemberLinkRepository).findByMember(orphan);
            will(returnValue(Arrays.asList())); // empty, so no links for orphan

            oneOf(mailChimpListMemberLinkRepository).findByMember(noOrphan);
            will(returnValue(Arrays.asList(new MailChimpListMemberLink()))); // a link for noOrphan
        }});


        // when
        List<MailChimpMember> orphanedMembers = mailChimpService.findOrphanedMembers();

        // then
        Assertions.assertThat(orphanedMembers.size()).isEqualTo(1);
        Assertions.assertThat(orphanedMembers).contains(orphan);

    }

}