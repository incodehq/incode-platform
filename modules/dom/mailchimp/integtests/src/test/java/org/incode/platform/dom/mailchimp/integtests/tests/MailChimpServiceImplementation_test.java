package org.incode.platform.dom.mailchimp.integtests.tests;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.mailchimp.dom.impl.MailChimpList;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLinkRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpListRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;
import org.incode.module.mailchimp.dom.impl.MailChimpMemberRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpServiceImplementation;
import org.incode.platform.dom.mailchimp.integtests.MailchimpModuleIntegTestAbstract;

public class MailChimpServiceImplementation_test extends MailchimpModuleIntegTestAbstract {


    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    MailChimpServiceImplementation mailChimpServiceImplementation;

    @Inject
    MailChimpListRepository mailChimpListRepository;

    @Inject
    MailChimpMemberRepository mailChimpMemberRepository;

    @Inject
    MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    MailChimpList list;

    MailChimpMember member;

    @Before
    public void setUp() throws Exception {
        // given
        list = mailChimpListRepository.findOrCreate("list1", "List 1");
        member = mailChimpMemberRepository.create("123", "firstName", "lastName", "email@test.com");
        mailChimpListMemberLinkRepository.findOrCreateLocal(list, member);
    }

    @Test
    public void deleteListLocal_works() throws Exception {

        // given
        Assertions.assertThat(mailChimpListRepository.listAll()).contains(list);
        Assertions.assertThat(list.getMembers()).contains(member);
        Assertions.assertThat(mailChimpListRepository.listAll()).isNotEmpty();
        // when
        mailChimpServiceImplementation.deleteListLocal(list);
        // then
        Assertions.assertThat(mailChimpListRepository.listAll()).isEmpty();
        Assertions.assertThat(mailChimpListRepository.listAll()).isEmpty();

    }


}