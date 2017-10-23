package org.incode.module.mailchimp.dom.api;

public interface IMailChimpParty {

    String getFirstName();

    String getLastName();

    String getEmailAddress();

    /**
     * When true the party will not be included in any list
     */
    Boolean excludeFromLists();

}
