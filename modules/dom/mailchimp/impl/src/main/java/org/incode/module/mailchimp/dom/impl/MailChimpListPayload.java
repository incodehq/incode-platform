package org.incode.module.mailchimp.dom.impl;

import lombok.Getter;
import lombok.Setter;

public class MailChimpListPayload {

    public MailChimpListPayload(
            final String company,
            final String addres1,
            final String city,
            final String zip,
            final String country,
            final String from_name,
            final String from_email,
            final String language) {
        this.contact = new Contact(company, addres1, city, zip, country);
        this.campaign_defaults = new Campaign_defaults(from_name, from_email, language);
    }

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Contact contact;

    @Getter @Setter
    private String permission_reminder;

    @Getter @Setter
    private Campaign_defaults campaign_defaults;

    @Getter @Setter
    private Boolean email_type_option;

    class Contact {

        public Contact(final String company, final String addres1, final String city, final String zip, final String country) {
            this.company = company;
            this.address1 = addres1;
            this.city = city;
            this.zip = zip;
            this.country = country;
        }

        @Getter @Setter
        private String company;

        @Getter @Setter
        private String address1;

        @Getter @Setter
        private String city;

        @Getter @Setter
        private String state;

        @Getter @Setter
        private String zip;

        @Getter @Setter
        private String country;

    }

    class Campaign_defaults {

        public Campaign_defaults(final String from_name, final String from_email, final String language) {
            this.from_name = from_name;
            this.from_email = from_email;
            this.language = language;
        }

        @Getter @Setter
        private String from_name;

        @Getter @Setter
        private String from_email;

        @Getter @Setter
        private String subject;

        @Getter @Setter
        private String language;

    }

}
