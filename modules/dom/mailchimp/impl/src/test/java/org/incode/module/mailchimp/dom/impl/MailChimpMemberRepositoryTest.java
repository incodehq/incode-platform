package org.incode.module.mailchimp.dom.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MailChimpMemberRepositoryTest {

    @Test
    public void findOrCreateLocal_checks_Iparty_exclude() throws Exception {

        // given
        MailChimpMemberRepository repository = new MailChimpMemberRepository();
        PartyForTest party = new PartyForTest(null, null,"some@email.adress",true);

        // when //then
        Assertions.assertThat(repository.findOrCreateLocal(party,null)).isNull();

    }

    @Test
    public void findOrCreateLocal_checks_Iparty_emailaddress() throws Exception {

        // given
        MailChimpMemberRepository repository = new MailChimpMemberRepository();
        PartyForTest party = new PartyForTest(null, null,null,false);

        // when //then
        Assertions.assertThat(repository.findOrCreateLocal(party,null)).isNull();

    }

}