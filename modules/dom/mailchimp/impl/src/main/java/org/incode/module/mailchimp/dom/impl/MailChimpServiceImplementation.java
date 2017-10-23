package org.incode.module.mailchimp.dom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.HttpMethod;

import com.google.gson.Gson;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.core.commons.config.IsisConfiguration;

import lombok.Getter;
import lombok.Setter;

@DomainService(nature = NatureOfService.DOMAIN)
public class MailChimpServiceImplementation {

    protected String getApiBaseUrl() {
        return this.configuration.getString("isis.service.mailchimp.base-url");
    }

    @Programmatic
    public List<MailChimpList> getAllListsFromRemote() {
        String resourceString = getApiBaseUrl().concat("/lists?&fields=lists.id,lists.name,lists.stats");
        MailChimpLists lists = unmarshalLists(mailChimpRestApiService.callRestApi(resourceString, HttpMethod.GET, null));
        List<MailChimpList> result = new ArrayList<>();
        for (MailChimpList.MailListMemberDto listDto : lists.getLists()){
            MailChimpList list = mailChimpListRepository.findOrCreate(listDto.getId(), listDto.getName());
            result.add(list);
        }
        return result;
    }

    @Programmatic
    public List<MailChimpList> getListsFromRemoteForComparison() {
        String resourceString = getApiBaseUrl().concat("/lists?&fields=lists.id,lists.name,lists.stats");
        MailChimpLists lists = unmarshalLists(mailChimpRestApiService.callRestApi(resourceString, HttpMethod.GET, null));
        List<MailChimpList> result = new ArrayList<>();
        for (MailChimpList.MailListMemberDto listDto : lists.getLists()){
            MailChimpList list = new MailChimpList();
            list.setListId(listDto.getId());
            result.add(list);
        }
        return result;
    }

    MailChimpLists unmarshalLists(final String json){
        Gson gson = new Gson();
        return  gson.fromJson(json, MailChimpLists.class);
    }

    @Programmatic
    public MailChimpList getMembers(final MailChimpList list) {
        String resourceString = getApiBaseUrl()
                .concat("/lists")
                .concat("/" + list.getListId()
                .concat("/members?count=2000&fields=members.id,members.email_address,members.status,members.merge_fields,members.stats,list_id,total_items"));
        MailChimpMembers remoteMembers = unmarshalMembers(mailChimpRestApiService.callRestApi(resourceString, HttpMethod.GET, null));
        for (MailChimpMember.MailChimpMemberDto memberDto : remoteMembers.getMembers()){
            mailChimpMemberRepository.findOrCreateFromRemote(
                    memberDto.getId(),
                    list,
                    memberDto.getMerge_fields().getFNAME(),
                    memberDto.getMerge_fields().getLNAME(),
                    memberDto.getEmail_address(),
                    memberDto.getStatus()
            );
        }
        return list;
    }

    MailChimpMembers unmarshalMembers(final String json){
        Gson gson = new Gson();
        return  gson.fromJson(json, MailChimpMembers.class);
    }

    @Programmatic
    public List<MailChimpMember> getRemoteMembersForComparison(final MailChimpList list) {
        String resourceString = getApiBaseUrl()
                .concat("/lists")
                .concat("/" + list.getListId()
                        .concat("/members?count=2000&fields=members.id,members.email_address,members.status,members.merge_fields,members.stats,list_id,total_items"));
        MailChimpMembers remoteMembers = unmarshalMembers(mailChimpRestApiService.callRestApi(resourceString, HttpMethod.GET, null));
        List<MailChimpMember> result = new ArrayList<>();
        for (MailChimpMember.MailChimpMemberDto memberDto : remoteMembers.getMembers()){
            MailChimpMember member = new MailChimpMember();
            member.setMemberId(memberDto.getId());
            result.add(member);
        }
        return result;
    }

    protected String getCompany() {
        return this.configuration.getString("isis.service.mailchimp.company");
    }
    protected String getAddres1() {
        return this.configuration.getString("isis.service.mailchimp.address1");
    }
    protected String getCity() {
        return this.configuration.getString("isis.service.mailchimp.city");
    }
    protected String getZip() {
        return this.configuration.getString("isis.service.mailchimp.zip");
    }
    protected String getCountry() {
        return this.configuration.getString("isis.service.mailchimp.country");
    }
    protected String getFromName() {
        return this.configuration.getString("isis.service.mailchimp.from_name");
    }
    protected String getFromEmail() {
        return this.configuration.getString("isis.service.mailchimp.from_email");
    }
    protected String getLanguage() {
        return this.configuration.getString("isis.service.mailchimp.language");
    }
    protected String getPermissionReminder() {
        return this.configuration.getString("isis.service.mailchimp.permissionreminder");
    }

    @Programmatic
    public String createList(final String name, final String defaultEmailSubject) throws Exception{
        MailChimpListPayload payloadObject = new MailChimpListPayload(
                getCompany(),
                getAddres1(),
                getCity(),
                getZip(),
                getCountry(),
                getFromName(),
                getFromEmail(),
                getLanguage()
        );
        payloadObject.setName(name);
        payloadObject.getCampaign_defaults().setSubject(defaultEmailSubject);
        payloadObject.setPermission_reminder(getPermissionReminder());
        payloadObject.setEmail_type_option(true);

        Gson gson = new Gson();
        String body = gson.toJson(payloadObject);

        String resourceString = getApiBaseUrl()
                .concat("/lists");

        return gson.fromJson(mailChimpRestApiService.callRestApi(resourceString, HttpMethod.POST, body), CreateListResponse.class).getId();

    }

    class CreateListResponse {
        @Getter @Setter
        private String id;
    }

    @Programmatic
    public MailChimpList createMember(final MailChimpMember member, final MailChimpList list){
        try {
            return  createMember(
                    member.getEmailAddress(),
                    member.getFirstName(),
                    member.getLastName(),
                    member,
                    list);
        } catch (Exception e){
            return list;
        }
    }

    @Programmatic
    public MailChimpList createMember(final String email, final String firstName, final String lastName, final MailChimpMember member, final MailChimpList list) throws Exception {

        MailChimpMember.MailChimpMemberDto memberDto = new MailChimpMember.MailChimpMemberDto();
        memberDto.getMerge_fields().setFNAME(firstName);
        memberDto.getMerge_fields().setLNAME(lastName);
        memberDto.setEmail_address(email);
        memberDto.setStatus("subscribed");
        Gson gson = new Gson();
        String body = gson.toJson(memberDto);

        String resourceString =
                getApiBaseUrl()
                        .concat("/lists")
                        .concat("/" + list.getListId())
                        .concat("/members");
        try {
            mailChimpRestApiService.callRestApi(resourceString, HttpMethod.POST, body);
            MailChimpListMemberLink link = mailChimpListMemberLinkRepository.findUnique(list, member);
            link.setNewLocal(false);
        } catch (Exception e) {
            messageService.warnUser(e.getMessage() + " member not created - maybe double email?");
            return null;
        }

        return list;

    }

    @Programmatic
    public MailChimpList deleteMember(final MailChimpMember member, final MailChimpList list) throws Exception {

        String resourceString =
                getApiBaseUrl()
                        .concat("/lists")
                        .concat("/" + list.getListId())
                        .concat("/members")
                        .concat("/" + member.getMemberId());
        if (mailChimpRestApiService.callRestApi(resourceString, HttpMethod.DELETE, null)=="deleted"){
            messageService.informUser("Member " + member.getFirstName() +  " deleted");
            MailChimpListMemberLink link = mailChimpListMemberLinkRepository.findUnique(list, member);
            if (link!=null){
                link.setIsDeletedRemote(true);
            }
        }
        return list;

    }

    @Programmatic
    public MailChimpList deleteList(final MailChimpList list) throws Exception {

        String resourceString =
                getApiBaseUrl()
                        .concat("/lists")
                        .concat("/" + list.getListId());
        if (mailChimpRestApiService.callRestApi(resourceString, HttpMethod.DELETE, null)=="deleted"){
            messageService.informUser("List " + list.getName() +  " deleted");
            list.setIsDeletedRemote(true);
        }
        return list;

    }

    @Programmatic
    public void deleteListLocal(final MailChimpList list){
        mailChimpListRepository.remove(list);
    }

    @Inject MailChimpRestApiService mailChimpRestApiService;

    @Inject MessageService messageService;

    @Inject IsisConfiguration configuration;

    @Inject MailChimpListRepository mailChimpListRepository;

    @Inject MailChimpMemberRepository mailChimpMemberRepository;

    @Inject MailChimpListMemberLinkRepository mailChimpListMemberLinkRepository;

}
