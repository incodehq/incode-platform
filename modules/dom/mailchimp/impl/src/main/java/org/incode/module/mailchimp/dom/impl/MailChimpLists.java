package org.incode.module.mailchimp.dom.impl;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MailChimpLists {

    @Getter @Setter
    private List<MailChimpList.MailListMemberDto> lists;

}
