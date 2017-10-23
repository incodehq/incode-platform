package org.incode.platform.dom.mailchimp.integtests.tests;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.incode.module.mailchimp.dom.impl.MailChimpList;
import org.incode.module.mailchimp.dom.impl.MailChimpListMemberLinkRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpListRepository;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;
import org.incode.module.mailchimp.dom.impl.MailChimpMemberRepository;
import org.incode.platform.dom.mailchimp.integtests.MailchimpModuleIntegTestAbstract;

public class MailChimpListMemberLinkRepository_test extends MailchimpModuleIntegTestAbstract {


    @Inject
    FixtureScripts fixtureScripts;

    @Inject TransactionService transactionService;

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
//        fixtureScripts.runFixtureScript(new TtiTearDown(), null);
        list = mailChimpListRepository.findOrCreate("list1", "List 1");
        member = mailChimpMemberRepository.findOrCreateFromRemote("1", list, "", "last name", "test@email.com", "some status");
    }

    @Test
    public void findUnique_works() throws Exception {

        // given
        mailChimpListMemberLinkRepository.findOrCreateFromRemote(list, member, "some status");
        transactionService.nextTransaction();
        // when, then
        Assertions.assertThat(mailChimpListMemberLinkRepository.findUnique(list, member)).isNotNull();

    }


}