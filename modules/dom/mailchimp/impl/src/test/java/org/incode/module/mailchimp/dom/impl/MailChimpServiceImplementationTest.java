package org.incode.module.mailchimp.dom.impl;

import java.util.List;

import javax.ws.rs.HttpMethod;

import org.assertj.core.api.Assertions;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

public class MailChimpServiceImplementationTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    IsisConfiguration isisConfiguration;

    @Mock
    MailChimpRestApiService mockRestService;

    @Mock
    MailChimpListRepository mailChimpListRepository;

    @Mock
    MailChimpMemberRepository mailChimpMemberRepository;

    private static String jsonListsResponse = "{ \"lists\": [ { \"id\": \"31897f659a\", \"name\": \"test123\", \"stats\": { \"member_count\": 1, \"unsubscribe_count\": 0, \"cleaned_count\": 0, \"member_count_since_send\": 0, \"unsubscribe_count_since_send\": 0, \"cleaned_count_since_send\": 0, \"campaign_count\": 0, \"campaign_last_sent\": \"\", \"merge_field_count\": 2, \"avg_sub_rate\": 0, \"avg_unsub_rate\": 0, \"target_sub_rate\": 0, \"open_rate\": 0, \"click_rate\": 0, \"last_sub_date\": \"2017-10-14T07:56:24+00:00\", \"last_unsub_date\": \"\" } } ] }\t\t";
    private static String jsonMembersResponse = "{ \"members\": [ { \"id\": \"caa694296dc2eaf4944b1d4eb9500e0b\", \"email_address\": \"johan@filternet.nl\", \"status\": \"subscribed\", \"merge_fields\": { \"FNAME\": \"Johan\", \"LNAME\": \"Doornenbal\" }, \"stats\": { \"avg_open_rate\": 0, \"avg_click_rate\": 0 } }, { \"id\": \"a512da9e85261f09ae4bdcd7c6b912d1\", \"email_address\": \"mietje@gmail.com\", \"status\": \"subscribed\", \"merge_fields\": { \"FNAME\": \"Mietje\", \"LNAME\": \"van der Meulen\" }, \"stats\": { \"avg_open_rate\": 0, \"avg_click_rate\": 0 } } ], \"list_id\": \"044aa09152\", \"total_items\": 2 }";

    @Test
    public void unmarshallLists_test() throws Exception {

        // given
        MailChimpServiceImplementation impl = new MailChimpServiceImplementation();

        // when
        MailChimpLists listsObj = impl.unmarshalLists(jsonListsResponse);

        // then
        Assertions.assertThat(listsObj.getLists().size()).isEqualTo(1);
        Assertions.assertThat(listsObj.getLists().get(0).getId()).isEqualTo("31897f659a");
        Assertions.assertThat(listsObj.getLists().get(0).getName()).isEqualTo("test123");
        Assertions.assertThat(listsObj.getLists().get(0).getMembers()).isEqualTo(null);

    }

    @Test
    public void getAllListsFromRemote_test() throws Exception {

        // given
        MailChimpServiceImplementation impl = new MailChimpServiceImplementation();
        impl.configuration = isisConfiguration;
        impl.mailChimpRestApiService = mockRestService;
        impl.mailChimpListRepository = mailChimpListRepository;

        // expect
        context.checking(new Expectations(){{
            oneOf(isisConfiguration).getString("isis.service.mailchimp.base-url");
            will(returnValue("baseUrl"));
            oneOf(mockRestService).callRestApi("baseUrl" + "/lists?&fields=lists.id,lists.name,lists.stats", HttpMethod.GET, null);
            will(returnValue(jsonListsResponse));
            allowing(mailChimpListRepository).findOrCreate(with(any(String.class)), with(any(String.class)));
        }});

        // when
        List<MailChimpList> result =  impl.getAllListsFromRemote();

        // then
        Assertions.assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void unmarshallMembers_test() throws Exception {

        // given
        MailChimpServiceImplementation impl = new MailChimpServiceImplementation();

        // when
        MailChimpMembers membersObj = impl.unmarshalMembers(jsonMembersResponse);

        // then
        Assertions.assertThat(membersObj.getMembers().size()).isEqualTo(2);
        Assertions.assertThat(membersObj.getMembers().get(0).getId()).isEqualTo("caa694296dc2eaf4944b1d4eb9500e0b");
        Assertions.assertThat(membersObj.getMembers().get(0).getMerge_fields().getFNAME()).isEqualTo("Johan");
        Assertions.assertThat(membersObj.getMembers().get(0).getMerge_fields().getLNAME()).isEqualTo("Doornenbal");
        Assertions.assertThat(membersObj.getMembers().get(0).getEmail_address()).isEqualTo("johan@filternet.nl");
        Assertions.assertThat(membersObj.getMembers().get(0).getStatus()).isEqualTo("subscribed");

    }

    @Test
    public void getMembers_test() throws Exception {

        // given
        MailChimpServiceImplementation impl = new MailChimpServiceImplementation();
        impl.configuration = isisConfiguration;
        impl.mailChimpRestApiService = mockRestService;
        impl.mailChimpMemberRepository = mailChimpMemberRepository;
        MailChimpList list = new MailChimpList();
        list.setListId("123");

        // expect
        context.checking(new Expectations(){{
            oneOf(isisConfiguration).getString("isis.service.mailchimp.base-url");
            will(returnValue("baseUrl"));
            oneOf(mockRestService).callRestApi("baseUrl" + "/lists" + "/123" + "/members?count=2000&fields=members.id,members.email_address,members.status,members.merge_fields,members.stats,list_id,total_items", HttpMethod.GET, null);
            will(returnValue(jsonMembersResponse));
            allowing(mailChimpMemberRepository).findOrCreateFromRemote(with(any(String.class)), with(any(MailChimpList.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)));
        }});

        // when
        MailChimpList result =  impl.getMembers(list);

    }

    @Mock
    MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

    @Test
    public void createMember_test() throws Exception {

        // given
        MailChimpServiceImplementation impl = new MailChimpServiceImplementation();
        impl.configuration = isisConfiguration;
        impl.mailChimpRestApiService = mockRestService;
        impl.mailChimpListMemberLinkRepository = mailChimpListMemberLinkRepository;

        MailChimpMember member = new MailChimpMember();
        MailChimpList list = new MailChimpList();
        list.setListId("list123");

        String bodyMemberCreation = "{\"email_address\":\"email\",\"status\":\"subscribed\",\"merge_fields\":{\"FNAME\":\"firstName\",\"LNAME\":\"lastName\"}}";

        MailChimpListMemberLink link = new MailChimpListMemberLink();
        link.setNewLocal(true);
        Assertions.assertThat(link.getNewLocal()).isTrue();

        // expect
        context.checking(new Expectations(){{
            oneOf(isisConfiguration).getString("isis.service.mailchimp.base-url");
            will(returnValue("baseUrl"));
            oneOf(mockRestService).callRestApi("baseUrl" + "/lists/list123/members", HttpMethod.POST, bodyMemberCreation);
            oneOf(mailChimpListMemberLinkRepository).findUnique(list, member);
            will(returnValue(link));
        }});

        // when
        impl.createMember("email", "firstName", "lastName", member,list);

        // then
        Assertions.assertThat(link.getNewLocal()).isFalse();

    }

    @Test
    public void createList_test() throws Exception {

        // given
        MailChimpServiceImplementation impl = new MailChimpServiceImplementation();
        impl.configuration = isisConfiguration;
        impl.mailChimpRestApiService = mockRestService;

        // expect
        context.checking(new Expectations(){{
            oneOf(isisConfiguration).getString("isis.service.mailchimp.company");
            will(returnValue("someCompany"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.address1");
            will(returnValue("someAddress"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.city");
            will(returnValue("someCity"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.zip");
            will(returnValue("someZip"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.country");
            will(returnValue("someCountry"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.from_name");
            will(returnValue("someName"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.from_email");
            will(returnValue("someEmail"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.language");
            will(returnValue("someLanguage"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.permissionreminder");
            will(returnValue("someReminder"));
            oneOf(isisConfiguration).getString("isis.service.mailchimp.base-url");
            will(returnValue("baseUrl"));
            oneOf(mockRestService).callRestApi(
                    "baseUrl" + "/lists",
                    HttpMethod.POST,
                    "{\"name\":\"Some new list name\","
                            + "\"contact\":{"
                            + "\"company\":\"someCompany\","
                            + "\"address1\":\"someAddress\","
                            + "\"city\":\"someCity\","
                            + "\"zip\":\"someZip\","
                            + "\"country\":\"someCountry\"},"
                            + "\"permission_reminder\":\"someReminder\","
                            + "\"campaign_defaults\":{"
                            + "\"from_name\":\"someName\","
                            + "\"from_email\":\"someEmail\","
                            + "\"subject\":\"default subject\","
                            + "\"language\":\"someLanguage\"},"
                            + "\"email_type_option\":true}");
            will(returnValue("{\"id\":\"someId\"}"));
        }});

        // when
        impl.createList("Some new list name", "default subject");

    }

}