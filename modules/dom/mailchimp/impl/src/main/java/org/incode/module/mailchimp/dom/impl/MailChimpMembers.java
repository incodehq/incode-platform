package org.incode.module.mailchimp.dom.impl;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MailChimpMembers {

    @Getter @Setter
    private List<MailChimpMember.MailChimpMemberDto> members;

}
