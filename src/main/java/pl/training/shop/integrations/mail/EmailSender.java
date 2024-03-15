package pl.training.shop.integrations.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailSender implements ApplicationRunner {

    private final JavaMailSender javaMailSender;
    private final TemplateService templateService;

    @Override
    public void run(ApplicationArguments args){
        var text = templateService.process("ChatNotification", Map.of("value", "Test message"), "pl");
        var message = createMail("trainer@sages.pl", new String[] { "client@training.pl" }, "Training", text);
        javaMailSender.send(message);
    }

    private MimeMessagePreparator createMail(String sender, String[] recipients, String subject, String text) {
        return mimeMessage -> {
            var message = new MimeMessageHelper(mimeMessage);
            message.setFrom(sender);
            message.setTo(recipients);
            message.setSubject(subject);
            message.setText(text, true);
        };
    }

}
