package pl.training.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Map;

// @Service
@RequiredArgsConstructor
public class EmailService implements ApplicationRunner {

    private final JavaMailSender mailSender;
    private final TemplateService templateService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var text = templateService.process("ChatNotification", Map.of("value", "Hello"), "pl");
        var message = createMessage("user@training.pl", new String[] { "client@training.pl" }, "Training", text);
        mailSender.send(message);
    }

    private MimeMessagePreparator createMessage(String sender, String[] recipients, String subject, String text) {
        return  mimeMessage -> {
            var message = new MimeMessageHelper(mimeMessage);
            message.setFrom(sender);
            message.setTo(recipients);
            message.setSubject(subject);
            message.setText(text, true);
        };
    }

}
