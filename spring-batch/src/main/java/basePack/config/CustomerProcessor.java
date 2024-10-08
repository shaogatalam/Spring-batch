package basePack.config;
import basePack.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerProcessor implements ItemProcessor<Customer,PersonalizedEmail> {

    //    private final EmailTemplateService emailTemplateService;
    //    public CustomerProcessor(EmailTemplateService emailTemplateService) {
    //        this.emailTemplateService = emailTemplateService;
    //    }


    @Override
    public PersonalizedEmail process(Customer customer) throws Exception {
        //String template = emailTemplateService.getTemplate();
        String emailTemplate = "Dear {name},\n\nWe are happy to serve you at {address}.\nYour revenue is {revenue}.\nThanks!";
        //System.out.println("MyCustomProcessor : Processing data : "+emailTemplate);
        // Personalize the email using customer data
        String personalizedContent = emailTemplate
                                        .replace("{name}", customer.getName())
                                        .replace("{address}", customer.getAddress())
                                        .replace("{revenue}", String.valueOf(customer.getRevenue()));
        //System.out.println(personalizedContent);
        // Return a new PersonalizedEmail object with recipient and content
        return new PersonalizedEmail(customer.getEmail(), personalizedContent);

    }
}