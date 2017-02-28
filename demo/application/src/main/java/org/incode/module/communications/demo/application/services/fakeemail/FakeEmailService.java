package org.incode.module.communications.demo.application.services.fakeemail;

import java.util.List;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.email.EmailService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "incodeCommunicationsDemo.FakeEmailService",
        menuOrder = "1"
)
@DomainServiceLayout(
        named = "Emails (Fake Server)"
)
public class FakeEmailService implements EmailService {

    @PostConstruct
    @Override
    public void init() {
    }

    private List<EmailMessage> messages = Lists.newArrayList();

    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "1")
    public List<EmailMessage> viewSentEmails() {
        return messages;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "2")
    public void removeSentEmails() {
        messages.clear();
    }

    @Programmatic
    @Override
    public boolean send(
            final List<String> to,
            final List<String> cc,
            final List<String> bcc,
            final String subject,
            final String body,
            final DataSource... attachments) {

        final EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(to);
        emailMessage.setCc(cc);
        emailMessage.setBcc(bcc);
        emailMessage.setSubject(subject);
        emailMessage.setBody(body);
        emailMessage.setAttachments(attachments);

        return true; // all OK
    }

    @Programmatic
    @Override
    public boolean isConfigured() {
        return true;
    }

}
