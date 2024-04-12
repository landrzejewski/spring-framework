package pl.training.integration.email;

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
    public void run(ApplicationArguments args) throws Exception {
        var text = templateService.process("ChatNotification", Map.of("value", "Test message"), "pl");
        var message = createMessage("training@sages.pl", new String[] { "client@training.pl"}, "Training", text);
        javaMailSender.send(message);
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
