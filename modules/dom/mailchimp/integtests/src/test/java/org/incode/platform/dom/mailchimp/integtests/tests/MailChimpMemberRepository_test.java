package org.incode.platform.dom.mailchimp.integtests.tests;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.mailchimp.dom.impl.MailChimpList;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLinkRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpListRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;
import org.incode.module.mailchimp.dom.impl.MailChimpMemberRepository;
import org.incode.platform.dom.mailchimp.integtests.MailchimpModuleIntegTestAbstract;

public class MailChimpMemberRepository_test extends MailchimpModuleIntegTestAbstract {


    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    MailChimpListRepository mailChimpListRepository;

    @Inject
    MailChimpMemberRepository mailChimpMemberRepository;

    @Inject
    MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    MailChimpList list;
    MailChimpMember member;

    @Test
    public void findOrCreate_works() throws Exception {

        MailChimpMember memberFound;

        // given
        list = mailChimpListRepository.findOrCreate("list1", "List 1");
        member = mailChimpMemberRepository.create("1", "", "last name", "test@email.com");
        mailChimpListMemberLinkRepository.findOrCreateFromRemote(list, member, "some status");
        // when
        memberFound = mailChimpMemberRepository.findOrCreateFromRemote("1", list, "tralala", "tralala", "tralala", "tralala");
        // then
        Assertions.assertThat(memberFound).isNotNull();
        Assertions.assertThat(memberFound).isEqualTo(member);
        Assertions.assertThat(mailChimpMemberRepository.listAll().size()).isEqualTo(1);
        Assertions.assertThat(mailChimpListMemberLinkRepository.listAll().size()).isEqualTo(1);

        // and when
        MailChimpList list2 = mailChimpListRepository.findOrCreate("list2", "List 2");
        memberFound = mailChimpMemberRepository.findOrCreateFromRemote("1", list2, "tralala", "tralala", "tralala", "tralala");
        // then
        Assertions.assertThat(memberFound).isEqualTo(member);
        Assertions.assertThat(mailChimpMemberRepository.listAll().size()).isEqualTo(1);
        Assertions.assertThat(mailChimpListMemberLinkRepository.listAll().size()).isEqualTo(2);

        // and when
        memberFound = mailChimpMemberRepository.findOrCreateFromRemote("2", list2, "tralala", "tralala", "tralala", "tralala");
        // then
        Assertions.assertThat(memberFound).isNotEqualTo(member);
        Assertions.assertThat(mailChimpMemberRepository.listAll().size()).isEqualTo(2);
        Assertions.assertThat(mailChimpListMemberLinkRepository.listAll().size()).isEqualTo(3);

    }

}