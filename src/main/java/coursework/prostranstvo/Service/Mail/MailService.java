package coursework.prostranstvo.Service.Mail;

import coursework.prostranstvo.Model.Exception.ApiException;
import coursework.prostranstvo.Model.DTO.Mail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(Mail mail) throws ApiException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("notify@prostranstvo.com");
            messageHelper.setTo(mail.getRecipient());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailContentBuilder.build(mail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Email sent");
        } catch (MailException e) {
            throw new ApiException("Error while sending mail to " + mail.getSubject());
        }
    }
}
