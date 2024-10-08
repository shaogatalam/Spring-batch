package basePack.config;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class EmailWriter implements ItemWriter<PersonalizedEmail> {

//    @Override
//    public void write(Chunk<? extends PersonalizedEmail> chunk) throws Exception {
//        System.out.println("Sending email to: ");
//    }

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void write(Chunk<? extends PersonalizedEmail> chunk) throws Exception {
        for (PersonalizedEmail personalizedEmail : chunk) {
            sendEmail(personalizedEmail);
        }
    }

    private void sendEmail(PersonalizedEmail personalizedEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("shaogat90@gmail.com");//(personalizedEmail.getRecipientEmail()); // Set recipient email
        message.setSubject("testmail");   // Set email subject
        message.setText("hiii");//(personalizedEmail.getEmailContent());         // Set email body
        message.setFrom("shaogat81@gmail.com");            // Set sender email
        mailSender.send(message);
        //System.out.println("Email sent to: " + personalizedEmail.getRecipientEmail());
    }

}


